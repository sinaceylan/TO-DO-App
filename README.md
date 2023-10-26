# TO-DO-App


## Take screenshots for the store
Things to do before proceeding with these steps:
1. **Un-commented Fastlane permissions in Android Manifest**
2. **Make sure you logged in**
3. **Make sure you are connected to the network**
4. **Make sure you have paired boat**

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

11. **Install Bundler and Create a `./Gemfile` in the Root Directory**  
    ```bash
    gem install bundler
    ```
    
12. **Add this as your first build step**  
    ```bash
    bundle install
    ```

16. **Then, You Can Start Taking Screenshots With:**  
    ```bash
    bundle exec fastlane
    ```
    
17. **Or, You Can Directly Use:**
       ```bash
       bundle exec fastlane capture_screenshots
       ```
