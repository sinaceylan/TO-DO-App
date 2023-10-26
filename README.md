# TO-DO-App

Below are the steps to set up Fastlane and Screengrab for this project.

## Fastlane Installation

### Steps:

1. **Check Ruby Version**
    ```bash
    ruby --version
    ```
    *Note: The version should be 2.5 or newer.*

2. **Check for Brew**  
    ```bash
    brew --version
    ```

3. **Install Brew** (If not installed)  
    ```bash
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    ```

4. **Download rbenv for External Ruby Installation**  
    ```bash
    brew install rbenv
    ```

5. **Add the Following to Your Profile**  
    ```bash
    eval "$(rbenv init -)"
    ```

6. **Check rbenv Version and Optionally Choose a Ruby Version**  
    ```bash
    rbenv install -l
    rbenv install 2.7.6
    ```

7. **Set the New Ruby Version as Global**  
    ```bash
    rbenv global 2.7.6
    ```

8. **Rehash rbenv**  
    ```bash
    rbenv rehash
    ```

9. **Check Which Ruby is Being Used**  
    ```bash
    which ruby
    ```

10. **Install Fastlane**  
    ```bash
    gem install fastlane -NV
    ```

11. **Install Bundler and Create a `./Gemfile` in the Root Directory**  
    ```bash
    gem install bundler
    ```

12. **If You Need to Add Some PATHs (e.g., export PATH="$HOME/.fastlane/bin:$PATH"), Use:**  
    ```bash
    vim  ~/.zshrc
    ```

13. **Since We Will Use Some APKs During the Screengrab Process, Create Some Files to Access These APKs**  
    ```bash
    ./gradlew assembleDebug assembleAndroidTest
    ```

14. **If You Get a Java Error, Most Likely Java is Not Installed on Your Computer. You Can Easily Install Java 11 With:**  
    ```bash
    brew install openjdk11
    ```

15. **Then, You Can Start Taking Screenshots With:**  
    ```bash
    bundle exec fastlane
    ```
    
16. **Or, You Can Directly Use:**
       ```bash
       bundle exec fastlane capture_screenshots
       ```
