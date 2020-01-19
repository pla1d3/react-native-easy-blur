require 'json'
package = JSON.parse(File.read(File.join(__dir__, './', 'package.json')))

Pod::Spec.new do |s|

  s.name            = "RNEasyBlur"
  s.version         = package['version']
  s.summary         = package['description']
  s.homepage        = package['homepage']
  s.author          = { "Simon Zhogot" => "xeywar@gmail.com" }
  s.source          = { :git => "https://github.com/simonprod/react-native-easy-blur.git", :tag => "#{s.version}" }
  s.platform        = :ios, '8.0'
  s.source_files    = 'ios/*.{h,m}'
  s.preserve_paths  = '*.js'
  s.dependency 'React'

end
