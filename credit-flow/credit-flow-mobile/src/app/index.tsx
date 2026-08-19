import { View, Text, StyleSheet, TextInput, ScrollView } from 'react-native'; //react native paketinden View , StyleSheet ve Text componentlerini ve yapılarını al.
import { useEffect, useState } from 'react'; //bunu reactten alıyoruz
import { PopPressable } from '@/components/pop-pressable';
//burası müsteriler ekranı.
type Customer = {//typescriptte bir nesnenin veri tipini tanımlama.
  id: number;
  customerNumber: string;
  fullName: string;
};

export default function CustomerScreen() {//style yok; nesnenin alanı doğrudan süslü parantez içinde gösteriliyor.
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isCustomerFormVisible, setIsCustomerFormVisible] = useState<boolean>(false);
  const [customerNumber, setCustomerNumber] = useState<string>('');
  const [fullName, setFullName] = useState<string>('');

  useEffect(() => {
    fetch('http://localhost:8080/api/customers')
      .then((response) => response.json())
      .then((customers: Customer[]) => {
        setCustomers(customers);
        setIsLoading(false);
      })
      .catch(() => {
        setErrorMessage('Müşteriler alınamadı');
        setIsLoading(false);
      });


  }, []);
  function createCustomer() {
    fetch(`http://localhost:8080/api/customers`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        customerNumber,
        fullName,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Müşteri Oluşturulamadı.');
        }
        return response.json();
      })
      .then((newCustomer: Customer) => {
        setCustomers((currentCustomers) => [
          newCustomer,
          ...currentCustomers,
        ]);
        setCustomerNumber('');
        setFullName('');
        setIsCustomerFormVisible(false);
        setErrorMessage(null);

      })
      .catch(() => {
        setErrorMessage('Müşteri oluşturulamadı');
      })
  };
  const isCustomerFormValid =
    customerNumber.trim().length > 0 && fullName.trim().length > 0;

  return (
    <ScrollView
      style={styles.screen}
      contentContainerStyle={styles.container}
      showsVerticalScrollIndicator={false}
    >
      <View style={styles.header}>
        <Text style={styles.eyebrow}>KREDİ TAKİP SİSTEMİ</Text>
        <Text style={styles.title}>Müşteriler</Text>
        <Text style={styles.subtitle}>
          Müşteri kayıtlarını görüntüleyin ve yeni müşteri oluşturun.
        </Text>
      </View>

      <PopPressable
        style={styles.primaryButton}
        onPress={() => {
          setIsCustomerFormVisible(true);
          setErrorMessage(null);
        }}
      >
        <Text style={styles.primaryButtonText}>+ Yeni Müşteri</Text>
      </PopPressable>
      {isCustomerFormVisible
        && <View style={styles.formCard}>
          <Text style={styles.formTitle}>Yeni müşteri kaydı</Text>
          <Text style={styles.formDescription}>Zorunlu alanları eksiksiz doldurun.</Text>
          <Text style={styles.inputLabel}>Müşteri numarası</Text>
          <TextInput
            placeholder='Müşteri Numarası'
            placeholderTextColor="#94a3b8"
            style={styles.input}
            value={customerNumber}
            onChangeText={setCustomerNumber}></TextInput>

          <Text style={styles.inputLabel}>Ad soyad</Text>
          <TextInput
            placeholder='Ad Soyad'
            placeholderTextColor="#94a3b8"
            style={styles.input}
            value={fullName}
            onChangeText={setFullName}></TextInput>
          <View style={styles.formActions}>
            <PopPressable
              style={[
                styles.primaryButton,
                styles.formButton,
                !isCustomerFormValid && styles.disabledButton,
              ]}
              disabled={!isCustomerFormValid}
              onPress={createCustomer}
            >
              <Text style={styles.primaryButtonText}>Kaydet</Text>
            </PopPressable>
            <PopPressable
              style={[styles.secondaryButton, styles.formButton]}
              onPress={() => {
                setIsCustomerFormVisible(false);
                setCustomerNumber('');
                setFullName('');
                setErrorMessage(null);
              }}
            >
              <Text style={styles.secondaryButtonText}>Vazgeç</Text>
            </PopPressable>
          </View>
        </View>}
      <View style={styles.sectionHeader}>
        <Text style={styles.sectionTitle}>Kayıtlı müşteriler</Text>
        <Text style={styles.countBadge}>{customers.length}</Text>
      </View>
      {
      /* Her müşteri için bir ekran öğesi oluşturur. */}
      {isLoading
        ? <View style={styles.messageCard}><Text style={styles.mutedText}>Müşteriler yükleniyor...</Text></View>
        : errorMessage
          ? <View style={styles.errorCard}><Text style={styles.errorText}>{errorMessage}</Text></View>
          : customers.map((customer) => (
            <View style={styles.customerCard} key={customer.id}>
              <View style={styles.avatar}>
                <Text style={styles.avatarText}>{customer.fullName.charAt(0).toUpperCase()}</Text>
              </View>
              <View style={styles.customerInfo}>
                <Text style={styles.customerName}>{customer.fullName}</Text>
                <Text style={styles.customerNumber}>{customer.customerNumber}</Text>
              </View>
              <View style={styles.idBadge}>
                <Text style={styles.idBadgeText}>ID {customer.id}</Text>
              </View>
            </View>
          ))}
    </ScrollView>
  );
}
const styles = StyleSheet.create({// stil seçiyoruz.
  screen: {
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
  eyebrow: {
    color: '#a85f78',
    fontSize: 12,
    fontWeight: '800',
    letterSpacing: 1.4,
  },
  title: {//yazı şekil boyutu
    color: '#33252b',
    fontSize: 34,
    fontWeight: '800',
    marginTop: 6,
  },
  subtitle: {
    fontSize: 15,
    lineHeight: 22,
    marginTop: 8,
    color: '#64748b',
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
  disabledButton: {
    opacity: 0.45,
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
    marginTop: 4,
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
  customerCard: {
    backgroundColor: '#fffafb',
    padding: 16,
    marginBottom: 12,
    borderRadius: 14,
    borderWidth: 1,
    borderColor: '#eadde2',
    borderLeftWidth: 4,
    borderLeftColor: '#d6a0b2',
    flexDirection: 'row',
    alignItems: 'center',
  },
  avatar: {
    width: 44,
    height: 44,
    borderRadius: 22,
    backgroundColor: '#f5e2e9',
    justifyContent: 'center',
    alignItems: 'center',
  },
  avatarText: {
    color: '#925069',
    fontSize: 17,
    fontWeight: '800',
  },
  customerInfo: {
    flex: 1,
    marginLeft: 13,
  },
  customerNumber: {
    fontSize: 13,
    color: '#64748b',
    marginTop: 3,
  },
  customerName: {
    color: '#33252b',
    fontSize: 16,
    fontWeight: '700',
  },
  idBadge: {
    paddingHorizontal: 9,
    paddingVertical: 5,
    borderRadius: 8,
    backgroundColor: '#f7edf1',
  },
  idBadgeText: {
    color: '#64748b',
    fontSize: 11,
    fontWeight: '700',
  },
  messageCard: {
    padding: 18,
    borderRadius: 12,
    backgroundColor: '#ffffff',
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
  },
});
