import React, { useState } from 'react';
import { View } from 'react-native';
import BlurView from './common';

export default ({ style, children })=> {
    const blurDefaultStlye = {
        top: 0,
        left: 0,
        position: 'absolute',
        height: style && style.height || '100%',
        width: style && style.width || '100%'
    };

    return (
        <View style={style}>
            <BlurView style={blurDefaultStlye}/>
            <View>{children}</View>
        </View>
    );
}
