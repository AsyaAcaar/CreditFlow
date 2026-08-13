import { View, Text, StyleSheet } from 'react-native';
import { useEffect, useState } from 'react';
type Loan = {
  id: number;
  loanNumber: string;
  customerFullName: string;
  customerId: number;
  principalAmount: number;
  termMonths: number;
  status: string;
};
export default function LoanScreen() {
  const [loans, setLoans] = useState<Loan[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  useEffect(() => {
    fetch('http://localhost:8080/api/loans')
      .then((response) => response.json())
      .then((data: Loan[]) => {
        setLoans(data);
        setIsLoading(false);
      })
      .catch(() => {
        setErrorMessage('Krediler alınamadı');
        setIsLoading(false);
      });
  }, []);
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Krediler</Text>
      {isLoading
        ? <Text>Yükleniyor...</Text>
        : errorMessage
          ? <Text>{errorMessage}</Text>
          : loans.map((loan) => (
            <View style={styles.loanCard} key={loan.id}>
              <Text>{loan.loanNumber}</Text>
              <Text>{loan.customerFullName}</Text>
              <Text>Tutar: {loan.principalAmount} TL</Text>
              <Text>Vade: {loan.termMonths} ay</Text>
              <Text>Durum: {loan.status}</Text>
            </View>
          ))}

    </View>);
}
const styles = StyleSheet.create({
  container: {
    padding: 24,
  },
  title: {
    fontSize: 28,
    fontWeight: '700',
    marginBottom: 20,
  },
  loanCard: {
    backgroundColor: '#f1f5f9',
    padding: 16,
    marginBottom: 12,
    borderRadius: 10,
  }
});
