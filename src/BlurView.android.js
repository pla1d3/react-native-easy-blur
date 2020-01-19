import React, { useState } from 'react';
import { View } from 'react-native';
import BlurView from './common';

export default ({ style, children })=> {
    const [parentId, setParentId] = useState(null);
    const [wrapperId, setWrapperId] = useState(null);
    const blurDefaultStlye = {
        top: 0,
        left: 0,
        position: 'absolute',
        height: style && style.height || '100%',
        width: style && style.width || '100%'
    };

    const onLayoutParent = e=> setParentId(e.nativeEvent.target);
    const onLayoutWrapper = e=> setTimeout(setWrapperId, 0, e.nativeEvent.target);

    return (
        <View style={style} onLayout={onLayoutParent}>
            <BlurView
                style={blurDefaultStlye}
                parentId={parentId}
                wrapperId={wrapperId}
            />
            <View onLayout={onLayoutWrapper}>
                {children}
            </View>
        </View>
    );
}
