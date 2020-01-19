#import "BlurView.h"

@interface BlurView()

@end

@implementation BlurView

-(instancetype)initWithFrame:(CGRect)frame {
  self = [super initWithFrame:frame];
  isBlur = false;
  return self;
}

-(void)layoutSubviews {
  [super layoutSubviews];
  
  if (!isBlur) {
    dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, NSEC_PER_SEC * 0.1);
    dispatch_after(delay, dispatch_get_main_queue(), ^(void){
      [self runBlur];
      self->isBlur = true;
    });
  }
}

-(void)runBlur {
  UIView *superview = self.superview;
  UIView *wrapperView = superview.subviews[1];
  UIImage *wrapperImage = [self toImage:superview];
  
  wrapperImage = [self addBorder:wrapperImage :3];
  wrapperImage = [self resizeImage:wrapperImage :100.0f];
  wrapperImage = [self blurImage:wrapperImage];
  
  CGRect boundingRect = CGRectMake(0, 0, superview.bounds.size.width, superview.bounds.size.height);
  [self updateBackground:wrapperImage :boundingRect];
  [wrapperView setHidden:YES];
}

-(UIImage*)toImage:(UIView*)view {
  UIGraphicsBeginImageContextWithOptions(view.bounds.size, NO, 0.0);
  [view.layer renderInContext:UIGraphicsGetCurrentContext()];
  UIImage* img = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  return img;
}

- (UIImage *)addBorder:(UIImage*)image :(int)size {
    UIGraphicsBeginImageContextWithOptions(image.size, NO, 0.0);
    [image drawInRect:CGRectMake(size, size, image.size.width - (2 * size), image.size.height  - (2 * size))];
    UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return newImage;
}

-(UIImage *)resizeImage:(UIImage *)image :(CGFloat)nwidth {
    float scale = nwidth / image.size.width;
    float newHeight = image.size.height * scale;

    UIGraphicsBeginImageContext(CGSizeMake(nwidth, newHeight));
    [image drawInRect:CGRectMake(0, 0, nwidth, newHeight)];
    UIImage *nImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return nImage;
}

-(UIImage*)blurImage:(UIImage*)image {
  CIContext *context = [CIContext contextWithOptions:nil];
  CIImage *inputImage = [CIImage imageWithCGImage:image.CGImage];
  
  CIFilter *filter = [CIFilter filterWithName:@"CIGaussianBlur"];
  [filter setValue:inputImage forKey:kCIInputImageKey];
  [filter setValue:[NSNumber numberWithFloat:1.0f] forKey:@"inputRadius"];
  CIImage *result = [filter valueForKey:kCIOutputImageKey];
  
  CGImageRef cgImage = [context createCGImage:result fromRect:[inputImage extent]];
  UIImage *retVal = [UIImage imageWithCGImage:cgImage];
  
  if (cgImage) {
    CGImageRelease(cgImage);
  }
  
  return retVal;
}

-(void)updateBackground:(UIImage*)image :(CGRect)boundingRect {
  self.frame = boundingRect;
  self.image = image;
  self.contentMode = UIViewContentModeScaleAspectFit;
}

@end
