#import "BlurView.h"
#import "BlurViewManager.h"

@implementation BlurViewManager

RCT_EXPORT_MODULE();

-(UIImageView*)view {
    return [[BlurView alloc] init];
}

@end
