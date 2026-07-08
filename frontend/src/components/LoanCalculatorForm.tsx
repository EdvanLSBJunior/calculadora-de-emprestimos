import { useState } from "react";
import {
  Alert,
  Box,
  Button,
  Container,
  Paper,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { NumericFormat } from "react-number-format";
import { calculateLoan } from "../services/loanCalculatorService";
import type { LoanCalculationResponse } from "../types/loan";
import LoanResultTable from "./LoanResultTable";

type LoanForm = {
  startDate: string;
  endDate: string;
  firstPaymentDate: string;
  loanAmount: string;
  interestRatePercent: string;
};

export default function LoanCalculatorForm() {
  const [form, setForm] = useState<LoanForm>({
    startDate: "",
    endDate: "",
    firstPaymentDate: "",
    loanAmount: "",
    interestRatePercent: "",
  });

  const [result, setResult] = useState<LoanCalculationResponse[]>([]);
  const [error, setError] = useState("");

  const isFormValid =
    form.startDate &&
    form.endDate &&
    form.firstPaymentDate &&
    Number(form.loanAmount) > 0 &&
    Number(form.interestRatePercent) > 0;

  async function handleSubmit(event: React.FormEvent) {
    event.preventDefault();
    setError("");

    try {
      const response = await calculateLoan({
        startDate: form.startDate,
        endDate: form.endDate,
        firstPaymentDate: form.firstPaymentDate,
        loanAmount: Number(form.loanAmount),
        interestRate: Number(form.interestRatePercent) / 100,
      });

      setResult(response);
    } catch {
      setResult([]);
      setError("Não foi possível calcular. Verifique os dados informados.");
    }
  }

  return (
    <Container
      maxWidth={false}
      sx={{
        py: 4,
        px: 2,
      }}
    >
      <Paper
        elevation={6}
        sx={{
          p: 5,
          borderRadius: 3,
        }}
      >
        <Typography
          component="h1"
          variant="h4"
          sx={{
            fontWeight: 700,
            color: "#1565c0",
            mb: 4,
            textAlign: "center",
            letterSpacing: 0.5,
          }}
        >
          Calculadora de Empréstimos
        </Typography>

        <form onSubmit={handleSubmit}>
          <Stack
            component="div"
            direction={{ xs: "column", md: "row" }}
            spacing={3}
            sx={{
              "& .MuiOutlinedInput-root": {
                borderRadius: 2,
                backgroundColor: "#fafafa",
              },
            }}
          >
            <TextField
              label="Data inicial"
              type="date"
              value={form.startDate}
              onChange={(e) =>
                setForm((prev) => ({ ...prev, startDate: e.target.value }))
              }
              slotProps={{
                inputLabel: {
                  shrink: true,
                },
              }}
              fullWidth
            />

            <TextField
              label="Data final"
              type="date"
              value={form.endDate}
              onChange={(e) =>
                setForm((prev) => ({ ...prev, endDate: e.target.value }))
              }
              slotProps={{
                inputLabel: {
                  shrink: true,
                },
              }}
              fullWidth
            />

            <TextField
              label="Primeiro pagamento"
              type="date"
              value={form.firstPaymentDate}
              onChange={(e) =>
                setForm((prev) => ({
                  ...prev,
                  firstPaymentDate: e.target.value,
                }))
              }
              slotProps={{
                inputLabel: {
                  shrink: true,
                },
              }}
              fullWidth
            />

            <NumericFormat
              customInput={TextField}
              label="Valor do empréstimo"
              value={form.loanAmount}
              thousandSeparator="."
              decimalSeparator=","
              decimalScale={2}
              fixedDecimalScale
              allowNegative={false}
              valueIsNumericString
              fullWidth
              onValueChange={(values) =>
                setForm((prev) => ({
                  ...prev,
                  loanAmount: values.value,
                }))
              }
            />

            <NumericFormat
              customInput={TextField}
              label="Taxa de juros (%)"
              value={form.interestRatePercent}
              decimalSeparator=","
              decimalScale={2}
              fixedDecimalScale
              allowNegative={false}
              valueIsNumericString
              fullWidth
              onValueChange={(values) =>
                setForm((prev) => ({
                  ...prev,
                  interestRatePercent: values.value,
                }))
              }
            />

            <Button
              type="submit"
              variant="contained"
              size="large"
              sx={{
                minWidth: 180,
                height: 59,
                fontWeight: 700,
                borderRadius: 2,
                textTransform: "none",
                fontSize: 16,
              }}
            >
              Calcular
            </Button>
          </Stack>
        </form>

        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

        <Box sx={{ mt: 8 }}>
          <LoanResultTable data={result} />
        </Box>
      </Paper>
    </Container>
  );
}
