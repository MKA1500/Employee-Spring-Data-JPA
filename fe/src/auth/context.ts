import { createContext } from "react";
import type { Credentials } from "../api/client";

export interface AuthState {
  username: string | null;
  login: (credentials: Credentials) => Promise<void>;
  logout: () => void;
}

export const AuthContext = createContext<AuthState | null>(null);
