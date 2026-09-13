import { useEffect, useState, type FormEvent } from "react";
import { apiRequest } from "../api/client";
import { describeError } from "../api/errors";
import type { EmployeeDetail } from "../types";

interface FormState {
  id?: number;
  department: string;
  rank: string;
  salary: string;
}

const emptyForm: FormState = { department: "", rank: "", salary: "" };

export function EmployeeDetailsPanel() {
  const [details, setDetails] = useState<EmployeeDetail[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<FormState>(emptyForm);
  const [saving, setSaving] = useState(false);

  async function load(): Promise<void> {
    try {
      const data = await apiRequest<EmployeeDetail[]>("/employeeDetails");
      setDetails(data);
      setError(null);
    } catch (err) {
      setError(describeError(err));
    } finally {
      setLoading(false);
    }
  }

  function handleRefresh(): void {
    setLoading(true);
    load();
  }

  useEffect(() => {
    let cancelled = false;
    (async () => {
      try {
        const data = await apiRequest<EmployeeDetail[]>("/employeeDetails");
        if (cancelled) return;
        setDetails(data);
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

  function startEdit(detail: EmployeeDetail): void {
    setForm({
      id: detail.id,
      department: detail.department ?? "",
      rank: detail.rank?.toString() ?? "",
      salary: detail.salary?.toString() ?? "",
    });
    setShowForm(true);
  }

  async function handleSubmit(event: FormEvent): Promise<void> {
    event.preventDefault();
    setSaving(true);
    setError(null);
    const payload: EmployeeDetail = {
      department: form.department || undefined,
      rank: form.rank ? Number(form.rank) : undefined,
      salary: form.salary ? Number(form.salary) : undefined,
    };
    try {
      if (form.id) {
        await apiRequest<EmployeeDetail>(`/employeeDetails/${form.id}`, {
          method: "PUT",
          body: JSON.stringify(payload),
        });
      } else {
        await apiRequest<EmployeeDetail>("/employeeDetails", {
          method: "POST",
          body: JSON.stringify(payload),
        });
      }
      setShowForm(false);
      await load();
    } catch (err) {
      setError(describeError(err));
    } finally {
      setSaving(false);
    }
  }

  async function handleDelete(id: number): Promise<void> {
    if (!confirm(`Delete employee detail #${id}?`)) return;
    setError(null);
    try {
      await apiRequest<void>(`/employeeDetails/${id}`, { method: "DELETE" });
      await load();
    } catch (err) {
      setError(describeError(err));
    }
  }

  return (
    <section>
      <div className="panel-toolbar">
        <h2>Employee Details</h2>
        <div>
          <button type="button" onClick={handleRefresh} disabled={loading}>
            Refresh
          </button>{" "}
          <button type="button" className="primary" onClick={startCreate}>
            New detail
          </button>
        </div>
      </div>

      <p className="hint">
        Standalone CRUD against /api/employeeDetails — a detail created here
        is not automatically linked to an employee.
      </p>

      {error && <div className="error-box">{error}</div>}

      {showForm && (
        <form className="form-card" onSubmit={handleSubmit}>
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
      ) : details.length === 0 ? (
        <p className="empty-state">No employee details found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Department</th>
              <th>Rank</th>
              <th>Salary</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {details.map((detail) => (
              <tr key={detail.id}>
                <td>{detail.id}</td>
                <td>{detail.department}</td>
                <td>{detail.rank}</td>
                <td>{detail.salary}</td>
                <td className="actions-cell">
                  <button type="button" onClick={() => startEdit(detail)}>
                    Edit
                  </button>
                  <button
                    type="button"
                    className="danger"
                    onClick={() => handleDelete(detail.id!)}
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
