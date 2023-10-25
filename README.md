# TO-DO-App

Steps:
Check Ruby Version
bash
Copy code
ruby --version
Note: The version should be 2.5 or newer.
Check for Brew
bash
Copy code
brew —version
Install Brew (If not installed)
bash
Copy code
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
Download rbenv for External Ruby Installation
bash
Copy code
brew install rbenv
Add the Following to Your Profile
bash
Copy code
eval "$(rbenv init -)"
Check rbenv Version and Optionally Choose a Ruby Version
bash
Copy code
rbenv install -l
rbenv install 2.7.6
Set the New Ruby Version as Global
bash
Copy code
rbenv global 2.7.6
Rehash rbenv
bash
Copy code
rbenv rehash
Check Which Ruby is Being Used
bash
Copy code
which ruby
Install Bundler and Create a ./Gemfile in the Root Directory
bash
Copy code
gem install bundler
Use This Every Time You Use Fastlane
bash
Copy code
bundle exec fastlane
Update Fastlane
bash
Copy code
bundle update fastlane
If You Need to Add Some PATHs (e.g., export PATH="$HOME/.fastlane/bin:$PATH"), Use:
bash
Copy code
vim  ~/.zshrc
After Following These Steps, Reset the Terminal or Type:
bash
Copy code
fastlane init
Screengrab Installation

Steps:
Install the Gem and Add Necessary Dependencies
bash
Copy code
gem install fastlane
Since We Will Use Some APKs During the Screengrab Process, Create Some Files to Access These APKs
bash
Copy code
./gradlew assembleDebug assembleAndroidTest
If You Get a Java Error, Most Likely Java is Not Installed on Your Computer. You Can Easily Install Java 11 With:
bash
Copy code
brew install openjdk11
Then, You Can Start Taking Screenshots With:
bash
Copy code
fastlane screengrab
