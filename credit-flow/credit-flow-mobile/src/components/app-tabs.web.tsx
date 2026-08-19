import {
  Tabs,
  TabList,
  TabTrigger,
  TabSlot,
  TabTriggerSlotProps,
  TabListProps,
} from 'expo-router/ui';
import { Pressable, View, StyleSheet, Text } from 'react-native';
import { useState } from 'react';

import { MaxContentWidth } from '@/constants/theme';

export default function AppTabs() {
  return (
    <Tabs>
      <TabSlot style={{ height: '100%' }} />
      <TabList asChild>
        <CustomTabList>
          <TabTrigger name="home" href="/" asChild>
            <TabButton>Müşteriler</TabButton>
          </TabTrigger>
          <TabTrigger name="explore" href="/explore" asChild>
            <TabButton>Krediler</TabButton>
          </TabTrigger>
        </CustomTabList>
      </TabList>
    </Tabs>
  );
}

export function TabButton({ children, isFocused, ...props }: TabTriggerSlotProps) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <Pressable
      {...props}
      onHoverIn={() => setIsHovered(true)}
      onHoverOut={() => setIsHovered(false)}
      style={({ pressed }) => [
        styles.tabButton,
        isFocused && styles.focusedTabButton,
        isHovered && styles.hoveredTabButton,
        pressed && styles.pressed,
      ]}
    >
      <Text style={[styles.tabButtonText, isFocused && styles.focusedTabButtonText]}>
        {children}
      </Text>
    </Pressable>
  );
}

export function CustomTabList(props: TabListProps) {
  return (
    <View {...props} style={styles.tabListContainer}>
      <View style={styles.innerContainer}>
        <View style={styles.brandMark}>
          <Text style={styles.brandMarkText}>CF</Text>
        </View>
        <Text style={styles.brandText}>CreditFlow</Text>

        {props.children}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  tabListContainer: {
    position: 'absolute',
    top: 0,
    zIndex: 20,
    width: '100%',
    paddingHorizontal: 24,
    paddingVertical: 16,
    justifyContent: 'center',
    alignItems: 'center',
    flexDirection: 'row',
    backgroundColor: '#fdf7f9',
  },
  innerContainer: {
    minHeight: 64,
    paddingVertical: 10,
    paddingHorizontal: 14,
    borderRadius: 16,
    flexDirection: 'row',
    alignItems: 'center',
    flexGrow: 1,
    gap: 8,
    maxWidth: MaxContentWidth,
    backgroundColor: '#fffafb',
    borderWidth: 1,
    borderColor: '#eadde2',
    shadowColor: '#7a3f54',
    shadowOffset: { width: 0, height: 6 },
    shadowOpacity: 0.08,
    shadowRadius: 18,
  },
  brandMark: {
    width: 38,
    height: 38,
    borderRadius: 11,
    backgroundColor: '#9f5870',
    justifyContent: 'center',
    alignItems: 'center',
  },
  brandMarkText: {
    color: '#ffffff',
    fontSize: 12,
    fontWeight: '900',
  },
  brandText: {
    color: '#33252b',
    fontSize: 16,
    fontWeight: '800',
    marginRight: 'auto',
  },
  pressed: {
    opacity: 0.7,
  },
  tabButton: {
    paddingVertical: 10,
    paddingHorizontal: 15,
    borderRadius: 10,
  },
  focusedTabButton: {
    backgroundColor: '#f5e2e9',
  },
  hoveredTabButton: {
    transform: [{ scale: 1.04 }],
    backgroundColor: '#f8eaf0',
  },
  tabButtonText: {
    color: '#64748b',
    fontSize: 14,
    fontWeight: '700',
  },
  focusedTabButtonText: {
    color: '#925069',
  },
});
