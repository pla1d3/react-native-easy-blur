import React from 'react';
import { View } from 'react-native';

export default ({ style, children }) => {
  return (
    <View style={style}>
      {children}
    </View>
  )
}
