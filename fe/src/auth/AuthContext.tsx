import { useState, type ReactNode } from "react";
import {
  ApiError,
  apiRequest,
  getCredentials,
  setCredentials,
  type Credentials,
} from "../api/client";
import { AuthContext } from "./context";

export function AuthProvider({ children }: { children: ReactNode }) {
  const [username, setUsername] = useState<string | null>(
    getCredentials()?.username ?? null,
  );

  async function login(credentials: Credentials): Promise<void> {
    setCredentials(credentials);
    try {
      // GET /api/employees requires role EMPLOYEE at minimum, so this both
      // verifies the password and confirms the account can reach the BE.
      await apiRequest("/employees");
      setUsername(credentials.username);
    } catch (err) {
      setCredentials(null);
      if (err instanceof ApiError && (err.status === 401 || err.status === 403)) {
        throw new Error(
          "Invalid username/password, or the account lacks the EMPLOYEE role.",
          { cause: err },
        );
      }
      throw err;
    }
  }

  function logout(): void {
    setCredentials(null);
    setUsername(null);
  }

  return (
    <AuthContext.Provider value={{ username, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
