export interface LoanCalculationRequest {
  startDate: string;
  endDate: string;
  firstPaymentDate: string;
  loanAmount: number;
  interestRate: number;
}

export interface LoanCalculationResponse {
  competenceDate: string;
  loanAmount: number;
  debtorBalance: number;
  installment: string;
  total: number;
  amortization: number;
  principalBalance: number;
  interestProvision: number;
  accumulatedInterest: number;
  paidInterest: number;
}