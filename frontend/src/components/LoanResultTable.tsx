import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import type { LoanCalculationResponse } from "../types/loan";

type Props = {
  data: LoanCalculationResponse[];
};

const formatCurrency = (value: number) =>
  value.toLocaleString("pt-BR", {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });

const formatDate = (value: string) =>
  new Date(value + "T00:00:00").toLocaleDateString("pt-BR");

const groupHeaderStyle = {
  backgroundColor: "#2f6d97",
  color: "#fff",
  fontWeight: 700,
  fontSize: 15,
  borderRight: "2px solid #d9edf7",
  borderBottom: "1px solid #d9edf7",
  position: "sticky",
  top: 0,
  zIndex: 3,
};

const columnHeaderStyle = {
  backgroundColor: "#3f7fa8",
  color: "#fff",
  fontWeight: 700,
  fontSize: 13,
  borderRight: "1px solid #d9edf7",
  borderBottom: "1px solid #d9edf7",
  whiteSpace: "nowrap",
  position: "sticky",
  top: 40,
  zIndex: 2,
};

const bodyCellStyle = {
  borderRight: "1px solid #e0e0e0",
  whiteSpace: "nowrap",
};

export default function LoanResultTable({ data }: Props) {
  if (!data.length) return null;

  return (
    <TableContainer
      component={Paper}
      sx={{
        mt: 3,
        maxHeight: "70vh",
        width: "100%",
        border: "1px solid #d0d0d0",
      }}
    >
      <Table stickyHeader size="small">
        <TableHead>
          <TableRow>
            <TableCell colSpan={3} align="center" sx={groupHeaderStyle}>
              Empréstimo
            </TableCell>
            <TableCell colSpan={2} align="center" sx={groupHeaderStyle}>
              Parcela
            </TableCell>
            <TableCell colSpan={2} align="center" sx={groupHeaderStyle}>
              Principal
            </TableCell>
            <TableCell colSpan={3} align="center" sx={groupHeaderStyle}>
              Juros
            </TableCell>
          </TableRow>

          <TableRow>
            <TableCell align="center" sx={columnHeaderStyle}>
              Data Competência
            </TableCell>
            <TableCell align="right" sx={columnHeaderStyle}>
              Valor de Empréstimo
            </TableCell>
            <TableCell
              align="right"
              sx={{
                ...columnHeaderStyle,
                borderRight: "3px solid #d9edf7",
              }}
            >
              Saldo Devedor
            </TableCell>

            <TableCell align="center" sx={columnHeaderStyle}>
              Consolidada
            </TableCell>
            <TableCell
              align="right"
              sx={{
                ...columnHeaderStyle,
                borderRight: "3px solid #d9edf7",
              }}
            >
              Total
            </TableCell>

            <TableCell align="right" sx={columnHeaderStyle}>
              Amortização
            </TableCell>
            <TableCell
              align="right"
              sx={{
                ...columnHeaderStyle,
                borderRight: "3px solid #d9edf7",
              }}
            >
              Saldo
            </TableCell>

            <TableCell align="right" sx={columnHeaderStyle}>
              Provisão
            </TableCell>
            <TableCell align="right" sx={columnHeaderStyle}>
              Acumulado
            </TableCell>
            <TableCell align="right" sx={columnHeaderStyle}>
              Pago
            </TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {data.map((row) => (
            <TableRow
              key={`${row.competenceDate}-${row.installment}`}
              hover
              sx={{
                "&:nth-of-type(even)": {
                  backgroundColor: "#fafafa",
                },
                "&:hover": {
                  backgroundColor: "#e3f2fd",
                },
              }}
            >
              <TableCell align="center" sx={bodyCellStyle}>
                {formatDate(row.competenceDate)}
              </TableCell>
              <TableCell align="right" sx={bodyCellStyle}>
                {formatCurrency(row.loanAmount)}
              </TableCell>
              <TableCell
                align="right"
                sx={{
                  ...bodyCellStyle,
                  borderRight: "3px solid #e0e0e0",
                }}
              >
                {formatCurrency(row.debtorBalance)}
              </TableCell>

              <TableCell align="center" sx={bodyCellStyle}>
                {row.installment}
              </TableCell>
              <TableCell
                align="right"
                sx={{
                  ...bodyCellStyle,
                  borderRight: "3px solid #e0e0e0",
                }}
              >
                {formatCurrency(row.total)}
              </TableCell>

              <TableCell align="right" sx={bodyCellStyle}>
                {formatCurrency(row.amortization)}
              </TableCell>
              <TableCell
                align="right"
                sx={{
                  ...bodyCellStyle,
                  borderRight: "3px solid #e0e0e0",
                }}
              >
                {formatCurrency(row.principalBalance)}
              </TableCell>

              <TableCell align="right" sx={bodyCellStyle}>
                {formatCurrency(row.interestProvision)}
              </TableCell>
              <TableCell align="right" sx={bodyCellStyle}>
                {formatCurrency(row.accumulatedInterest)}
              </TableCell>
              <TableCell align="right" sx={bodyCellStyle}>
                {formatCurrency(row.paidInterest)}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
