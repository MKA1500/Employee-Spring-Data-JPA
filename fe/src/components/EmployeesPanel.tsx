import { useEffect, useState, type FormEvent } from "react";
import { apiRequest } from "../api/client";
import { describeError } from "../api/errors";
import type { Employee } from "../types";

interface EmployeeFormState {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  employeeDetailId?: number;
  department: string;
  rank: string;
  salary: string;
}

const emptyForm: EmployeeFormState = {
  firstName: "",
  lastName: "",
  email: "",
  phoneNumber: "",
  department: "",
  rank: "",
  salary: "",
};

export function EmployeesPanel() {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<EmployeeFormState>(emptyForm);
  const [saving, setSaving] = useState(false);

  async function loadEmployees(): Promise<void> {
    try {
      const data = await apiRequest<Employee[]>("/employees");
      setEmployees(data);
      setError(null);
    } catch (err) {
      setError(describeError(err));
    } finally {
      setLoading(false);
    }
  }

  function handleRefresh(): void {
    setLoading(true);
    loadEmployees();
  }

  useEffect(() => {
    let cancelled = false;
    (async () => {
      try {
        const data = await apiRequest<Employee[]>("/employees");
        if (cancelled) return;
        setEmployees(data);
        setError(null);
      } catch (err) {
        if (!cancelled) setError(describeError(err));
      } finally {
        if (!cancelled) setLoading(false);
      }
    })();
    return () => {
      cancelled = true;
    };
  }, []);

  function startCreate(): void {
    setForm(emptyForm);
    setShowForm(true);
  }

  function startEdit(employee: Employee): void {
    setForm({
      id: employee.id,
      firstName: employee.firstName,
      lastName: employee.lastName,
      email: employee.email,
      phoneNumber: employee.phoneNumber ?? "",
      employeeDetailId: employee.employeeDetail?.id,
      department: employee.employeeDetail?.department ?? "",
      rank: employee.employeeDetail?.rank?.toString() ?? "",
      salary: employee.employeeDetail?.salary?.toString() ?? "",
    });
    setShowForm(true);
  }

  async function handleSubmit(event: FormEvent): Promise<void> {
    event.preventDefault();
    setSaving(true);
    setError(null);

    const payload: Employee = {
      firstName: form.firstName,
      lastName: form.lastName,
      email: form.email,
      phoneNumber: form.phoneNumber || undefined,
      employeeDetail: {
        id: form.employeeDetailId,
        department: form.department || undefined,
        rank: form.rank ? Number(form.rank) : undefined,
        salary: form.salary ? Number(form.salary) : undefined,
      },
    };

    try {
      if (form.id) {
        await apiRequest<Employee>(`/employees/${form.id}`, {
          method: "PUT",
          body: JSON.stringify(payload),
        });
      } else {
        await apiRequest<Employee>("/employees", {
          method: "POST",
          body: JSON.stringify(payload),
        });
      }
      setShowForm(false);
      await loadEmployees();
    } catch (err) {
      setError(describeError(err));
    } finally {
      setSaving(false);
    }
  }

  async function handleDelete(id: number): Promise<void> {
    if (!confirm(`Delete employee #${id}?`)) return;
    setError(null);
    try {
      await apiRequest<void>(`/employees/${id}`, { method: "DELETE" });
      await loadEmployees();
    } catch (err) {
      setError(describeError(err));
    }
  }

  return (
    <section>
      <div className="panel-toolbar">
        <h2>Employees</h2>
        <div>
          <button type="button" onClick={handleRefresh} disabled={loading}>
            Refresh
          </button>{" "}
          <button type="button" className="primary" onClick={startCreate}>
            New employee
          </button>
        </div>
      </div>

      {error && <div className="error-box">{error}</div>}

      {showForm && (
        <form className="form-card" onSubmit={handleSubmit}>
          <label>
            First name
            <input
              required
              value={form.firstName}
              onChange={(e) => setForm({ ...form, firstName: e.target.value })}
            />
          </label>
          <label>
            Last name
            <input
              required
              value={form.lastName}
              onChange={(e) => setForm({ ...form, lastName: e.target.value })}
            />
          </label>
          <label>
            Email
            <input
              required
              type="email"
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
            />
          </label>
          <label>
            Phone
            <input
              value={form.phoneNumber}
              onChange={(e) => setForm({ ...form, phoneNumber: e.target.value })}
            />
          </label>
          <label>
            Department
            <input
              value={form.department}
              onChange={(e) => setForm({ ...form, department: e.target.value })}
            />
          </label>
          <label>
            Rank
            <input
              type="number"
              value={form.rank}
              onChange={(e) => setForm({ ...form, rank: e.target.value })}
            />
          </label>
          <label>
            Salary
            <input
              type="number"
              value={form.salary}
              onChange={(e) => setForm({ ...form, salary: e.target.value })}
            />
          </label>
          <div className="form-actions">
            <button type="submit" className="primary" disabled={saving}>
              {form.id ? "Save changes" : "Create"}
            </button>
            <button type="button" onClick={() => setShowForm(false)}>
              Cancel
            </button>
          </div>
        </form>
      )}

      {loading ? (
        <p className="hint">Loading…</p>
      ) : employees.length === 0 ? (
        <p className="empty-state">No employees found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Department</th>
              <th>Rank</th>
              <th>Salary</th>
              <th>Benefits</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {employees.map((employee) => (
              <tr key={employee.id}>
                <td>{employee.id}</td>
                <td>
                  {employee.firstName} {employee.lastName}
                </td>
                <td>{employee.email}</td>
                <td>{employee.phoneNumber}</td>
                <td>{employee.employeeDetail?.department ?? "—"}</td>
                <td>{employee.employeeDetail?.rank ?? "—"}</td>
                <td>{employee.employeeDetail?.salary ?? "—"}</td>
                <td>{employee.benefits?.length ?? 0}</td>
                <td className="actions-cell">
                  <button type="button" onClick={() => startEdit(employee)}>
                    Edit
                  </button>
                  <button
                    type="button"
                    className="danger"
                    onClick={() => handleDelete(employee.id!)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}
