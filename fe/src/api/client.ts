export interface Credentials {
  username: string;
  password: string;
}

const STORAGE_KEY = "employee-client:credentials";

export function loadCredentials(): Credentials | null {
  try {
    const raw = sessionStorage.getItem(STORAGE_KEY);
    return raw ? (JSON.parse(raw) as Credentials) : null;
  } catch {
    return null;
  }
}

export function saveCredentials(credentials: Credentials | null): void {
  try {
    if (credentials) {
      sessionStorage.setItem(STORAGE_KEY, JSON.stringify(credentials));
    } else {
      sessionStorage.removeItem(STORAGE_KEY);
    }
  } catch {
    // ignore storage errors (e.g. private browsing)
  }
}

const API_BASE_URL: string =
  import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api";

export class ApiError extends Error {
  status: number;
  body: unknown;

  constructor(status: number, body: unknown) {
    super(`Request failed with status ${status}`);
    this.name = "ApiError";
    this.status = status;
    this.body = body;
  }
}

let currentCredentials: Credentials | null = loadCredentials();

export function setCredentials(credentials: Credentials | null): void {
  currentCredentials = credentials;
  saveCredentials(credentials);
}

export function getCredentials(): Credentials | null {
  return currentCredentials;
}

export async function apiRequest<T>(
  path: string,
  options: RequestInit = {},
): Promise<T> {
  const headers = new Headers(options.headers);
  headers.set("Content-Type", "application/json");
  if (currentCredentials) {
    headers.set(
      "Authorization",
      `Basic ${btoa(`${currentCredentials.username}:${currentCredentials.password}`)}`,
    );
  }

  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers,
  });

  if (response.status === 204) {
    return undefined as T;
  }

  const text = await response.text();
  const data = text ? JSON.parse(text) : undefined;

  if (!response.ok) {
    throw new ApiError(response.status, data);
  }

  return data as T;
}
