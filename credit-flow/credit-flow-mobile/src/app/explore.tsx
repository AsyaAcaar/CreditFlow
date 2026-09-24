import { View, Text, StyleSheet, ScrollView, TextInput } from 'react-native';
import { useEffect, useState } from 'react';
import { PopPressable } from '@/components/pop-pressable';
type Loan = {
  id: number;
  loanNumber: string;

  customerFullName: string;
  customerId: number;
  principalAmount: number;
  termMonths: number;
  status: string;

};
type Customer = {
  id: number;
  customerNumber: string;
  creditFlowId: number;
  fullName: string;
};
type Installment = {
  id: number;
  loanId: number;
  installmentNumber: number;
  amount: number;
  dueDate: string;
  status: string;
  paidAt: string | null;

}

export default function LoanScreen() {
  const [loans, setLoans] = useState<Loan[]>([]);
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [selectedLoan, setSelectedLoan] = useState<Loan | null>(null);
  const [installments, setInstallments] = useState<Installment[]>([]);
  const [confirmingInstallmentId, setConfirmingInstallmentId] = useState<number | null>(null);
  const [isLoanFormVisible, setIsLoanFormVisible] = useState<boolean>(false);
  const [loanNumber, setLoanNumber] = useState<string>('');
  const [selectedCustomerId, setSelectedCustomerId] = useState<number | null>(null);
  const [principalAmount, setPrincipalAmount] = useState<string>('');
  const [termMonths, setTermMonths] = useState<string>('');
  useEffect(() => {
    Promise.all([
      fetch('http://localhost:8080/api/loans'),
      fetch('http://localhost:8080/api/customers'),
    ])
      .then(([loanResponse, customerResponse]) => {
        if (!loanResponse.ok || !customerResponse.ok) {
          throw new Error('Kredi veya müşteri bilgileri alınamadı');
        }

        return Promise.all([
          loanResponse.json(),
          customerResponse.json(),
        ]);
      })
      .then(([loanData, customerData]: [Loan[], Customer[]]) => {
        setLoans(loanData);
        setCustomers(customerData);
        setIsLoading(false);
      })
      .catch(() => {
        setErrorMessage('Kredi veya müşteri bilgileri alınamadı');
        setIsLoading(false);
      });
  }, []);
  function loadInstallments(loan: Loan) {
    setSelectedLoan(loan);
    setIsLoading(true);
    setErrorMessage(null);
    fetch(`http://localhost:8080/api/loans/${loan.id}/installments`)
      .then((response) => response.json())
      .then((data: Installment[]) => {
        setInstallments(data);
        setIsLoading(false);
      })
      .catch(() => {
        setErrorMessage('Taksitler Alınamadı.')
        setIsLoading(false);
      })
  }
  function createLoan() {
    if (selectedCustomerId === null) {
      setErrorMessage('Lütfen bir müşteri seçin');
      return;
    }

    fetch('http://localhost:8080/api/loans', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        loanNumber,
        customerId: selectedCustomerId,
        principalAmount: Number(principalAmount),
        termMonths: Number(termMonths)
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Yeni kredi oluşturulamadı');
        }

        return response.json();
      })
      .then((newLoan: Loan) => {
        setLoans((currentLoans) => [
          newLoan,
          ...currentLoans,
        ]);

        setLoanNumber('');
        setSelectedCustomerId(null);
        setPrincipalAmount('');
        setTermMonths('');
        setIsLoanFormVisible(false);
        setErrorMessage(null);
      })
      .catch(() => {
        setErrorMessage('Yeni kredi oluşturulamadı');
      });

  }
  function payInstallment(installmentId: number) {
    fetch(`http://localhost:8080/api/installments/${installmentId}/pay`, {
      method: 'PATCH',
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Taksit Ödenemedi');
        }

        return response.json();
      })
      .then((paidInstallment: Installment) => {
        setInstallments((currentInstallments) =>
          currentInstallments.map((installment) =>
            installment.id === paidInstallment.id
              ? paidInstallment
              : installment
          )
        );
        setConfirmingInstallmentId(null);
      })
      .catch(() => {
        setErrorMessage('Taksit ödenemedi');
      })
  }
  const isLoanFormValid =
    loanNumber.trim().length > 0 &&
    selectedCustomerId !== null &&
    Number(principalAmount) > 0 &&
    [6, 12, 24, 36].includes(Number(termMonths));

  if (selectedLoan) {
    return (
      <ScrollView
        style={styles.scroll}
        contentContainerStyle={styles.container}
        showsVerticalScrollIndicator={true}
      >
        <PopPressable
          style={styles.backButton}
          onPress={() => {
            setSelectedLoan(null);
            setErrorMessage(null);
          }}
        >
          <Text style={styles.backButtonText}>← Kredilere dön</Text>
        </PopPressable>

        <View style={styles.detailHeader}>
          <Text style={styles.eyebrow}>KREDİ DETAYI</Text>
          <Text style={styles.title}>{selectedLoan.loanNumber}</Text>
          <Text style={styles.subtitle}>{selectedLoan.customerFullName}</Text>
        </View>
        {isLoading ? (
          <View style={styles.messageCard}><Text style={styles.mutedText}>Taksitler yükleniyor...</Text></View>
        ) : errorMessage ? (
          <View style={styles.errorCard}><Text style={styles.errorText}>{errorMessage}</Text></View>
        ) : (
          installments.map((installment) => (
            <View key={installment.id} style={styles.loanCard}>
              <View style={styles.cardTopRow}>
                <Text style={styles.cardTitle}>Taksit {installment.installmentNumber}</Text>
                <View style={[
                  styles.statusBadge,
                  installment.status === 'PAID' ? styles.paidBadge : styles.pendingBadge,
                ]}>
                  <Text style={[
                    styles.statusText,
                    installment.status === 'PAID' ? styles.paidText : styles.pendingText,
                  ]}>
                    {installment.status === 'PAID' ? 'ÖDENDİ' : installment.status}
                  </Text>
                </View>
              </View>
              <Text style={styles.amountText}>{installment.amount.toLocaleString('tr-TR')} TL</Text>
              <Text style={styles.metaText}>Son ödeme tarihi: {installment.dueDate}</Text>
              {installment.status === 'PENDING' &&
                confirmingInstallmentId !== installment.id && (
                  <PopPressable
                    style={styles.payButton}
                    onPress={() => setConfirmingInstallmentId(installment.id)}
                  >
                    <Text style={styles.payButtonText}>Taksidi öde</Text>
                  </PopPressable>
                )}
              {installment.status === 'PENDING' &&
                confirmingInstallmentId === installment.id && (
                  <View style={styles.confirmationBox}>
                    {/* Yanlışlıkla ödeme yapılmasını önlemek için onay adımı gösteriyoruz. */}

                    <Text style={styles.confirmationText}>Ödemeyi onaylıyor musunuz?</Text>
                    <View style={styles.formActions}>
                      <PopPressable style={[styles.primaryButton, styles.formButton]} onPress={() => payInstallment(installment.id)}>
                        <Text style={styles.primaryButtonText}>Onayla</Text>
                      </PopPressable>
                      <PopPressable style={[styles.secondaryButton, styles.formButton]} onPress={() => setConfirmingInstallmentId(null)}>
                        <Text style={styles.secondaryButtonText}>Vazgeç</Text>
                      </PopPressable>
                    </View>
                  </View>
                )}
            </View>
          ))
        )}
      </ScrollView>
    );
  }
  return (
    <ScrollView
      style={styles.scroll}
      contentContainerStyle={styles.container}
      showsVerticalScrollIndicator={true}
    >
      <View style={styles.header}>
        <Text style={styles.eyebrow}>KREDİ TAKİP SİSTEMİ</Text>
        <Text style={styles.title}>Krediler</Text>
        <Text style={styles.subtitle}>
          Aktif kredileri, vadeleri ve ödeme planlarını yönetin.
        </Text>
      </View>
      <PopPressable
        style={styles.primaryButton}
        onPress={() => {
          setIsLoanFormVisible(true);
          setErrorMessage(null);
        }}
      >
        <Text style={styles.primaryButtonText}>+ Yeni Kredi</Text>
      </PopPressable>
      {isLoanFormVisible && (
        <View style={styles.formCard}>
          <Text style={styles.formTitle}>Yeni kredi oluştur</Text>
          <Text style={styles.formDescription}>Müşteriyi seçin ve kredi bilgilerini girin.</Text>
          <Text style={styles.inputLabel}>Kredi numarası</Text>
          <TextInput
            placeholder='Kredi Numarası'
            placeholderTextColor="#94a3b8"
            style={styles.input}
            value={loanNumber}
            onChangeText={setLoanNumber}
          ></TextInput>
          <Text style={styles.customerSelectionTitle}>Müşteri seç</Text>
          {customers.map((customer) => {
            const isSelected = selectedCustomerId === customer.id;
            const hasActiveLoan = loans.some(
              (loan) => loan.customerId === customer.id && loan.status === 'ACTIVE'
            );

            return (
              <PopPressable
                key={customer.id}
                disabled={hasActiveLoan}
                style={[
                  styles.customerOption,
                  isSelected && styles.selectedCustomerOption,
                  hasActiveLoan && styles.unavailableCustomerOption,
                ]}
                onPress={() => setSelectedCustomerId(customer.id)}
              >
                <Text style={[
                  styles.customerOptionText,
                  isSelected && styles.selectedCustomerText,
                  hasActiveLoan && styles.unavailableCustomerText,
                ]}>
                  {customer.customerNumber} - {customer.fullName}
                </Text>
                {hasActiveLoan && (
                  <Text style={styles.activeLoanHint}>Aktif kredisi var</Text>
                )}
              </PopPressable>
            );
          })}
          <Text style={styles.inputLabel}>Kredi tutarı</Text>
          <TextInput
            placeholder='Kredi Tutarı'
            placeholderTextColor="#94a3b8"
            style={styles.input}
            value={principalAmount}
            onChangeText={setPrincipalAmount}
            keyboardType="numeric"
          ></TextInput>
          <Text style={styles.inputLabel}>Vade</Text>
          <View style={styles.termOptions}>
            {[6, 12, 24, 36].map((month) => {
              const isSelected = termMonths === String(month);

              return (
                <PopPressable
                  key={month}
                  style={[styles.termOption, isSelected && styles.selectedTermOption]}
                  onPress={() => setTermMonths(String(month))}
                >
                  <Text style={[styles.termOptionText, isSelected && styles.selectedTermOptionText]}>
                    {month} ay
                  </Text>
                </PopPressable>
              );
            })}
          </View>
          <View style={styles.formActions}>
            <PopPressable
              onPress={createLoan}
              disabled={!isLoanFormValid}
              style={[
                styles.primaryButton,
                styles.formButton,
                !isLoanFormValid && styles.disabledButton,
              ]}
            >
              <Text style={styles.primaryButtonText}>Kaydet</Text>
            </PopPressable>

            <PopPressable
              style={[styles.secondaryButton, styles.formButton]}
              onPress={() => {
                setIsLoanFormVisible(false);
                setLoanNumber('');
                setSelectedCustomerId(null);
                setPrincipalAmount('');
                setTermMonths('');
                setErrorMessage(null);
              }}
            >
              <Text style={styles.secondaryButtonText}>Vazgeç</Text>
            </PopPressable>
          </View>
        </View>
      )}
      <View style={styles.sectionHeader}>
        <Text style={styles.sectionTitle}>Kredi portföyü</Text>
        <Text style={styles.countBadge}>{loans.length}</Text>
      </View>
      {isLoading
        ? <View style={styles.messageCard}><Text style={styles.mutedText}>Krediler yükleniyor...</Text></View>
        : errorMessage
          ? <View style={styles.errorCard}><Text style={styles.errorText}>{errorMessage}</Text></View>
          : loans.map((loan) => (
            <PopPressable
              key={loan.id}
              style={styles.loanCard}
              onPress={() => loadInstallments(loan)}
            >
              <View style={styles.cardTopRow}>
                <Text style={styles.cardTitle}>{loan.loanNumber}</Text>
                <View style={[
                  styles.statusBadge,
                  loan.status === 'ACTIVE' ? styles.activeBadge : styles.closedBadge,
                ]}>
                  <Text style={[
                    styles.statusText,
                    loan.status === 'ACTIVE' ? styles.activeText : styles.closedText,
                  ]}>
                    {loan.status === 'ACTIVE' ? 'AKTİF' : 'KAPALI'}
                  </Text>
                </View>
              </View>
              <Text style={styles.customerName}>{loan.customerFullName}</Text>
              <Text style={styles.amountText}>{loan.principalAmount.toLocaleString('tr-TR')} TL</Text>
              <View style={styles.cardFooter}>
                <Text style={styles.metaText}>{loan.termMonths} ay vade</Text>
                <Text style={styles.detailLink}>Taksitleri görüntüle →</Text>
              </View>
            </PopPressable>
          ))}

    </ScrollView>);
}
const styles = StyleSheet.create({
  scroll: {
    flex: 1,
    backgroundColor: '#fdf7f9',
  },
  container: {
    padding: 24,
    paddingTop: 112,
    paddingBottom: 64,
    width: '100%',
    maxWidth: 820,
    alignSelf: 'center',
  },
  header: {
    marginBottom: 24,
  },
  detailHeader: {
    marginTop: 24,
    marginBottom: 22,
  },
  eyebrow: {
    color: '#a85f78',
    fontSize: 12,
    fontWeight: '800',
    letterSpacing: 1.4,
  },
  title: {
    color: '#33252b',
    fontSize: 34,
    fontWeight: '800',
    marginTop: 6,
  },
  subtitle: {
    color: '#64748b',
    fontSize: 15,
    lineHeight: 22,
    marginTop: 8,
  },
  primaryButton: {
    minHeight: 46,
    paddingHorizontal: 18,
    paddingVertical: 12,
    borderRadius: 12,
    backgroundColor: '#9f5870',
    borderWidth: 1,
    borderColor: '#87465c',
    shadowColor: '#7a3f54',
    shadowOffset: { width: 0, height: 5 },
    shadowOpacity: 0.2,
    shadowRadius: 10,
    justifyContent: 'center',
    alignItems: 'center',
    alignSelf: 'flex-start',
  },
  primaryButtonText: {
    color: '#ffffff',
    fontSize: 15,
    fontWeight: '700',
  },
  secondaryButton: {
    minHeight: 46,
    paddingHorizontal: 18,
    paddingVertical: 12,
    borderRadius: 12,
    borderWidth: 1,
    borderColor: '#dfc8d0',
    backgroundColor: '#fffafb',
    justifyContent: 'center',
    alignItems: 'center',
  },
  secondaryButtonText: {
    color: '#334155',
    fontSize: 15,
    fontWeight: '700',
  },
  backButton: {
    alignSelf: 'flex-start',
    paddingHorizontal: 14,
    paddingVertical: 9,
    borderRadius: 10,
    backgroundColor: '#f5e7ec',
  },
  backButtonText: {
    color: '#75495a',
    fontSize: 14,
    fontWeight: '700',
  },
  formCard: {
    marginTop: 18,
    padding: 20,
    borderRadius: 16,
    backgroundColor: '#fffafb',
    borderWidth: 1,
    borderColor: '#ead6dd',
    shadowColor: '#8b5265',
    shadowOffset: { width: 0, height: 5 },
    shadowOpacity: 0.08,
    shadowRadius: 16,
  },
  formTitle: {
    color: '#33252b',
    fontSize: 19,
    fontWeight: '800',
  },
  formDescription: {
    color: '#64748b',
    fontSize: 14,
    marginTop: 4,
    marginBottom: 18,
  },
  inputLabel: {
    color: '#334155',
    fontSize: 13,
    fontWeight: '700',
    marginBottom: 7,
  },
  input: {
    minHeight: 48,
    borderWidth: 1,
    borderColor: '#dfc8d0',
    borderRadius: 10,
    paddingHorizontal: 14,
    marginBottom: 16,
    backgroundColor: '#ffffff',
    color: '#33252b',
    fontSize: 15,
  },
  formActions: {
    flexDirection: 'row',
    gap: 10,
    marginTop: 8,
  },
  formButton: {
    flex: 1,
    alignSelf: 'stretch',
  },
  sectionHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 30,
    marginBottom: 12,
  },
  sectionTitle: {
    color: '#33252b',
    fontSize: 18,
    fontWeight: '800',
  },
  countBadge: {
    marginLeft: 9,
    paddingHorizontal: 9,
    paddingVertical: 3,
    borderRadius: 12,
    backgroundColor: '#f5e2e9',
    color: '#925069',
    fontSize: 12,
    fontWeight: '800',
  },
  loanCard: {
    backgroundColor: '#fffafb',
    padding: 18,
    marginBottom: 12,
    borderRadius: 14,
    borderWidth: 1,
    borderColor: '#eadde2',
    borderLeftWidth: 4,
    borderLeftColor: '#d6a0b2',
  },
  cardTopRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    gap: 12,
  },
  cardTitle: {
    color: '#33252b',
    fontSize: 16,
    fontWeight: '800',
  },
  customerName: {
    color: '#475569',
    fontSize: 14,
    marginTop: 7,
  },
  amountText: {
    color: '#33252b',
    fontSize: 23,
    fontWeight: '800',
    marginTop: 16,
  },
  cardFooter: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    gap: 12,
    marginTop: 14,
  },
  metaText: {
    color: '#64748b',
    fontSize: 13,
    marginTop: 6,
  },
  detailLink: {
    color: '#9f5870',
    fontSize: 13,
    fontWeight: '700',
  },
  statusBadge: {
    paddingHorizontal: 9,
    paddingVertical: 5,
    borderRadius: 20,
  },
  statusText: {
    fontSize: 10,
    fontWeight: '900',
    letterSpacing: 0.5,
  },
  activeBadge: {
    backgroundColor: '#dcfce7',
  },
  activeText: {
    color: '#15803d',
  },
  closedBadge: {
    backgroundColor: '#e2e8f0',
  },
  closedText: {
    color: '#475569',
  },
  paidBadge: {
    backgroundColor: '#dcfce7',
  },
  paidText: {
    color: '#15803d',
  },
  pendingBadge: {
    backgroundColor: '#ffedd5',
  },
  pendingText: {
    color: '#c2410c',
  },
  payButton: {
    minHeight: 42,
    marginTop: 16,
    paddingHorizontal: 16,
    borderRadius: 10,
    backgroundColor: '#9f5870',
    borderWidth: 1,
    borderColor: '#87465c',
    shadowColor: '#7a3f54',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.16,
    shadowRadius: 8,
    justifyContent: 'center',
    alignItems: 'center',
    alignSelf: 'flex-start',
  },
  payButtonText: {
    color: '#ffffff',
    fontWeight: '700',
  },
  confirmationBox: {
    marginTop: 16,
    padding: 14,
    borderRadius: 12,
    backgroundColor: '#fcf0f4',
    borderWidth: 1,
    borderColor: '#eac7d3',
  },
  confirmationText: {
    color: '#7a3f54',
    fontSize: 14,
    fontWeight: '700',
    marginBottom: 5,
  },
  customerSelectionTitle: {
    color: '#334155',
    fontSize: 13,
    fontWeight: '700',
    marginBottom: 8,
  },
  customerOption: {
    borderWidth: 1,
    borderColor: '#dfc8d0',
    padding: 12,
    marginBottom: 8,
    borderRadius: 10,
    backgroundColor: '#fffafb',
  },
  customerOptionText: {
    color: '#334155',
    fontSize: 14,
    fontWeight: '600',
  },
  selectedCustomerOption: {
    backgroundColor: '#9f5870',
    borderColor: '#87465c',
  },
  selectedCustomerText: {
    color: '#ffffff',
    fontWeight: '700',
  },
  unavailableCustomerOption: {
    backgroundColor: '#f1f5f9',
    borderColor: '#e2e8f0',
    opacity: 0.65,
  },
  unavailableCustomerText: {
    color: '#94a3b8',
  },
  activeLoanHint: {
    color: '#b45309',
    fontSize: 11,
    fontWeight: '700',
    marginTop: 4,
  },
  termOptions: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 8,
    marginBottom: 16,
  },
  termOption: {
    paddingHorizontal: 15,
    paddingVertical: 10,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#dfc8d0',
    backgroundColor: '#fffafb',
  },
  termOptionText: {
    color: '#475569',
    fontSize: 13,
    fontWeight: '700',
  },
  selectedTermOption: {
    backgroundColor: '#f5e2e9',
    borderColor: '#a85f78',
  },
  selectedTermOptionText: {
    color: '#925069',
  },
  disabledButton: {
    opacity: 0.45,
  },
  messageCard: {
    padding: 18,
    borderRadius: 12,
    backgroundColor: '#fffafb',
  },
  mutedText: {
    color: '#64748b',
  },
  errorCard: {
    padding: 16,
    borderRadius: 12,
    backgroundColor: '#fef2f2',
    borderWidth: 1,
    borderColor: '#fecaca',
  },
  errorText: {
    color: '#b91c1c',
    fontWeight: '600',
  }
});
