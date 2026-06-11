# Spring AI + Gemini Quick Start

## Step 1: Get a Free Gemini API Key

Go to:

https://aistudio.google.com

1. Sign in with your Google account.
2. Click **Get API Key**.
3. Create a new API key.
4. Copy the generated key.

This usually takes less than 2 minutes.

---

## Step 2: Create a Spring Boot Project

Use:

* Java 21
* Spring Boot 3.5.x
* Maven

### Maven Dependencies

```xml
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-starter-model-google-genai</artifactId>
    </dependency>

</dependencies>
```

---

## Step 3: Configure Gemini

Create `application.yml`:

```yaml
spring:
  ai:
    google:
      genai:
        gemini:
          api-key: ${GCP_API_KEY}
```

---

## Step 4: Create a Simple Runner

```java
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

     @Bean
  	CommandLineRunner runner(ChatClient.Builder builder) {
  
  		return args -> {
  
  			ChatClient chatClient = builder.build();
  
  			String logContent = Files.readString(
  					Paths.get("src/main/resources/error.log"));
  
  			String response = chatClient.prompt()
  					.user("""
       				
  					 Rules:
  							- severity must be LOW, MEDIUM, HIGH, or CRITICAL
  							- rootCause max 5 words
  							- fix max 5 words
  							- prevention max 5 words					
  
  					{
  					  "severity":"",
  					  "rootCause":"",
  					  "fix":"",
  					  "prevention":""
  					}
                
                      Exception:
                      """ + logContent)
  					.call()
  					.content();
  
  			System.out.println("\n========== AI ANALYSIS ==========");
  			System.out.println(response);
  			System.out.println("================================\n");
  
  		};
    }
}
```

---

## Step 5: Set the API Key

### Windows CMD

```cmd
set GOOGLE_API_KEY=YOUR_KEY
```

### PowerShell

```powershell
$env:GOOGLE_API_KEY="YOUR_KEY"
```

---

## Step 6: Run the Application

```bash
mvn spring-boot:run
```

### Sample Output

```text
========== AI ANALYSIS ==========
```json
{
  "severity": "MEDIUM",
  "rootCause": "User object was null",
  "fix": "Add null check",
  "prevention": "Validate input, handle nulls"
}
```
================================

```
