import { View, Text, StyleSheet } from 'react-native'; //react native paketinden View , StyleSheet ve Text componentlerini ve yapılarını al.
import { useEffect, useState } from 'react'; //bunu reactten alıyoruz
//burası müsteriler ekranı.
type Customer = {//typescriptte bir nesnenin veri tipini tanımlama.
  id: number;
  customerNumber: string;
  fullName: string;
};

export default function CustomerScreen(){//style yok; nesnenin alanı doğrudan süslü parantez içinde gösteriliyor.
  const [customers,setCustomers] = useState<Customer[]>([]); 
  const[isLoading,setIsLoading] = useState<boolean>(true);
  const[errorMessage,setErrorMessage] = useState<string | null >(null);
  useEffect(() => {
    fetch('http://localhost:8080/api/customers')
    .then((response) => response.json())
    .then((customers: Customer[])=> {
      setCustomers(customers);
    setIsLoading(false);
    })
    .catch(() => {
      setErrorMessage('Müşteriler alınamadı');
      setIsLoading(false);
    });


  },[]);
  return(
    <View style={styles.container}>
      <Text style={styles.title}>CreditFlow</Text>
      <Text style={styles.subtitle}>Müşteriler</Text>{
      /* Her müşteri için bir ekran öğesi oluşturur. */}
      {isLoading
      ? <Text>Bekleniyor...</Text>
      :errorMessage
      ?<Text>{errorMessage}</Text>
      :customers.map((customer) => (
    <View style={styles.customerCard} key={customer.id}>
      <Text style={styles.customerNumber}>{customer.customerNumber}</Text>
      <Text style={styles.customerName}>{customer.fullName}</Text>
    </View>
  ))}
    </View>
  );
} 
const styles=StyleSheet.create({// stil seçiyoruz.
  container:{
    padding:24,
  },
  title: {//yazı şekil boyutu
    fontSize: 28,
    fontWeight: '700',
  },
  subtitle:{
    fontSize: 20,
    marginTop: 8,
    marginBottom: 20,
    color: '#334155',
  },
  customerCard:{
    backgroundColor: '#f1f5f9',
    padding: 16,
    marginBottom: 12,
    borderRadius: 10,

  },
  customerNumber:{
    fontSize: 13,
    color: '#64748b',
  },
  customerName:{
    fontSize: 17,
    fontWeight: '600',
    marginTop: 4,
  },
});