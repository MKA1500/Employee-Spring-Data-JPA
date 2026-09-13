export interface EmployeeDetail {
  id?: number;
  department?: string;
  rank?: number;
  salary?: number;
}

export interface EmployeeBenefit {
  id?: number;
  name: string;
  cost: number;
  // Note: the BE excludes this from JSON responses (Jackson @JsonBackReference),
  // it is only used when creating a benefit for a given employee.
  employee?: { id: number };
}

export interface Employee {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber?: string;
  employeeDetail?: EmployeeDetail;
  benefits?: EmployeeBenefit[];
}
