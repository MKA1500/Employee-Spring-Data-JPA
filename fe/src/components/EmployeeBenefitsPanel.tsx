import { useEffect, useState, type FormEvent } from "react";
import { apiRequest } from "../api/client";
import { describeError } from "../api/errors";
import type { EmployeeBenefit } from "../types";

interface FormState {
  name: string;
  cost: string;
  employeeId: string;
}

const emptyForm: FormState = { name: "", cost: "", employeeId: "" };

export function EmployeeBenefitsPanel() {
  const [benefits, setBenefits] = useState<EmployeeBenefit[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<FormState>(emptyForm);
  const [saving, setSaving] = useState(false);

  async function load(): Promise<void> {
    try {
      const data = await apiRequest<EmployeeBenefit[]>("/employeeBenefits");
      setBenefits(data);
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
        const data = await apiRequest<EmployeeBenefit[]>("/employeeBenefits");
        if (cancelled) return;
        setBenefits(data);
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

  async function handleSubmit(event: FormEvent): Promise<void> {
    event.preventDefault();
    setSaving(true);
    setError(null);
    const payload = {
      name: form.name,
      cost: Number(form.cost),
      employee: { id: Number(form.employeeId) },
    };
    try {
      await apiRequest<EmployeeBenefit>("/employeeBenefits", {
        method: "POST",
        body: JSON.stringify(payload),
      });
      setForm(emptyForm);
      setShowForm(false);
      await load();
    } catch (err) {
      setError(describeError(err));
    } finally {
      setSaving(false);
    }
  }

  async function handleDelete(id: number): Promise<void> {
    if (!confirm(`Delete benefit #${id}?`)) return;
    setError(null);
    try {
      await apiRequest<void>(`/employeeBenefits/${id}`, { method: "DELETE" });
      await load();
    } catch (err) {
      setError(describeError(err));
    }
  }

  return (
    <section>
      <div className="panel-toolbar">
        <h2>Employee Benefits</h2>
        <div>
          <button type="button" onClick={handleRefresh} disabled={loading}>
            Refresh
          </button>{" "}
          <button
            type="button"
            className="primary"
            onClick={() => setShowForm((s) => !s)}
          >
            New benefit
          </button>
        </div>
      </div>

      <p className="hint">
        The BE omits the owning employee from this list's JSON
        (Jackson @JsonBackReference) — check the Employees tab for
        per-employee benefit counts. Create a benefit by employee ID below.
      </p>

      {error && <div className="error-box">{error}</div>}

      {showForm && (
        <form className="form-card" onSubmit={handleSubmit}>
          <label>
            Name
            <input
              required
              value={form.name}
              onChange={(e) => setForm({ ...form, name: e.target.value })}
            />
          </label>
          <label>
            Cost
            <input
              required
              type="number"
              value={form.cost}
              onChange={(e) => setForm({ ...form, cost: e.target.value })}
            />
          </label>
          <label>
            Employee ID
            <input
              required
              type="number"
              value={form.employeeId}
              onChange={(e) => setForm({ ...form, employeeId: e.target.value })}
            />
          </label>
          <div className="form-actions">
            <button type="submit" className="primary" disabled={saving}>
              Create
            </button>
            <button type="button" onClick={() => setShowForm(false)}>
              Cancel
            </button>
          </div>
        </form>
      )}

      {loading ? (
        <p className="hint">Loading…</p>
      ) : benefits.length === 0 ? (
        <p className="empty-state">No employee benefits found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Cost</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {benefits.map((benefit) => (
              <tr key={benefit.id}>
                <td>{benefit.id}</td>
                <td>{benefit.name}</td>
                <td>{benefit.cost}</td>
                <td className="actions-cell">
                  <button
                    type="button"
                    className="danger"
                    onClick={() => handleDelete(benefit.id!)}
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
