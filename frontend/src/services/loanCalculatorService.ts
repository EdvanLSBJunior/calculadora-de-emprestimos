import axios from 'axios';
import type { LoanCalculationRequest, LoanCalculationResponse } from '../types/loan';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

export async function calculateLoan(
  request: LoanCalculationRequest
): Promise<LoanCalculationResponse[]> {
  const response = await api.post<LoanCalculationResponse[]>(
    '/loan-calculator/calculate',
    request
  );

  return response.data;
}