import { ApiError } from "./client";

export function describeError(err: unknown): string {
  if (err instanceof ApiError) {
    const body =
      typeof err.body === "string"
        ? err.body
        : err.body
          ? JSON.stringify(err.body, null, 2)
          : "";
    return `HTTP ${err.status}${body ? `\n${body}` : ""}`;
  }
  if (err instanceof Error) return err.message;
  return "Unknown error";
}
