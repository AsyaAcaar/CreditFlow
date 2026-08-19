import { useState } from 'react';
import { Pressable, type PressableProps, StyleSheet } from 'react-native';

export function PopPressable({
  style,
  onHoverIn,
  onHoverOut,
  ...props
}: PressableProps) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <Pressable
      {...props}
      onHoverIn={(event) => {
        setIsHovered(true);
        onHoverIn?.(event);
      }}
      onHoverOut={(event) => {
        setIsHovered(false);
        onHoverOut?.(event);
      }}
      style={(state) => [
        typeof style === 'function' ? style(state) : style,
        isHovered && !props.disabled && styles.hovered,
        state.pressed && styles.pressed,
      ]}
    />
  );
}

const styles = StyleSheet.create({
  hovered: {
    transform: [{ scale: 1.018 }],
    shadowColor: '#7a3f54',
    shadowOffset: { width: 0, height: 7 },
    shadowOpacity: 0.18,
    shadowRadius: 12,
  },
  pressed: {
    transform: [{ scale: 0.985 }],
    opacity: 0.9,
  },
});
