
# Nectar API Tokens Service

NectarAPI is a microservices-based, integrated meter device management (MDM) and head-end system (HES) tool for prepaid, STSEd2 meters. It is developed to support high availability for small, medium and large utilities and is intended to be deployed on kubernetes or similar orchestrators. NectarAPI allows utilities to generate and decode IEC62055-41 tokens using its internal virtual HSM or a Prism HSM via the Prism Thrift API. In addition, it allows for subscriber, meter and utility management and multiple STS configurations can be managed using the NectarAPI. NectarAPI uses an API-first approach and exposes feature-rich, REST API endpoints that allow for token generation/decoding, subscribers/users/utility management, logging e.t.c. NectarAPI's virtual HSM is IEC62055-41:2018 (STS6) compliant and supports DES (DKGA02) and KDF-HMAC-SHA-256 (DKGA04) as well as STA (EA07) and MISTY1 (EA11).

The tokens-service is one of the micro-services required to run the NectarAPI. This service is responsible to the generation of STS IEC62055-41 tokens and for the decoding of these tokens via REST API endpoints. The tokens-service allows the generation of the following types of STS tokens:


* Class 0: Transfer Credit
* Class 1: Initiate Meter Test/Display
* Class 2: SetMaximum Power Limit
* Class 2: Clear Credit
* Class 2: KeyChange Tokens for 64/128 bit Decoder Key Transfer
* Class 2: SetTariffRate
* Class 2: ClearTamperCondition
* Class 2: SetMaximum PhasePowerUnbalanceLimit
* Class 2: SetWaterMeterFactor

NectarAPI uses the [BouncyCastle](https://www.bouncycastle.org/) Java implementation of cryptographic algorithms. The following STS encryption algorithms are implemented in this service:

* STA: EncryptionAlgorithm07
* DEA: EncryptionAlgorithm09
* MISTY1: EncryptionAlgorithm11


# Built with

NectarAPI tokens-service is built using Springboot version 3, OpenJDK (Java) version 17, Redis and Gradle8 for builds. 

# Getting Started

To run the tokens service, first run the [nectar-db](https://github.com/NectarAPI/nectar-db) service and ensure that the credentials and port match those defined in this service's `/src/main/resources/application.yml` configurations. 

Then use gradle to deploy and run the service

`./gradlew build -x test && ./gradlew bootRun`


You should have output similar to the following:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.1)

2024-02-07T17:12:10.379Z  INFO 1 --- [           main] k.c.n.t.NectarTokensServiceApplication   : Starting NectarTokensServiceApplication using Java 17.0.10 with PID 1 (/etc/tokens-service/tokens-service.jar started by root in /etc/tokens-service)
2024-02-07T17:12:10.387Z  INFO 1 --- [           main] k.c.n.t.NectarTokensServiceApplication   : No active profile set, falling back to 1 default profile: "default"
2024-02-07T17:12:11.475Z  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2024-02-07T17:12:11.475Z  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2024-02-07T17:12:11.534Z  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 43 ms. Found 1 JPA repository interface.
2024-02-07T17:12:11.753Z  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2024-02-07T17:12:11.754Z  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Redis repositories in DEFAULT mode.
2024-02-07T17:12:11.779Z  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 16 ms. Found 0 Redis repository interfaces.
2024-02-07T17:12:12.604Z  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8084 (http)
2024-02-07T17:12:12.616Z  INFO 1 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-02-07T17:12:12.616Z  INFO 1 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.17]
2024-02-07T17:12:12.653Z  INFO 1 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-02-07T17:12:12.654Z  INFO 1 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2132 ms
2024-02-07T17:12:12.849Z  INFO 1 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2024-02-07T17:12:12.899Z  INFO 1 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.4.1.Final
2024-02-07T17:12:12.930Z  INFO 1 --- [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2024-02-07T17:12:13.158Z  INFO 1 --- [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2024-02-07T17:12:13.183Z  INFO 1 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2024-02-07T17:12:14.230Z  INFO 1 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection@2e929182
2024-02-07T17:12:14.233Z  INFO 1 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2024-02-07T17:12:14.271Z  WARN 1 --- [           main] org.hibernate.orm.deprecation            : HHH90000025: PostgreSQLDialect does not need to be specified explicitly using 'hibernate.dialect' (remove the property setting and it will be selected by default)
2024-02-07T17:12:15.183Z  INFO 1 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2024-02-07T17:12:15.229Z  INFO 1 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2024-02-07T17:12:16.527Z  INFO 1 --- [           main] o.s.d.j.r.query.QueryEnhancerFactory     : Hibernate is in classpath; If applicable, HQL parser will be used.
2024-02-07T17:12:21.898Z  WARN 1 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2024-02-07T17:12:23.634Z  INFO 1 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Will secure any request with [org.springframework.security.web.session.DisableEncodeUrlFilter@27e80cfa, org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@519b726a, org.springframework.s...
2024-02-07T17:12:26.862Z  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8084 (http) with context path ''
2024-02-07T17:12:26.902Z  INFO 1 --- [           main] k.c.n.t.NectarTokensServiceApplication   : Started NectarTokensServiceApplication in 17.255 seconds (process running for 17.925)

```

# Usage

While the `tokens-service` may be run independent of other NectarAPI micro-services, it is recommended that the nectar-deploy script be used to launch the tokens-service as part of NectarAPI. REST API access may then be available via the [api-gateway](https://github.com/NectarAPI/api-gateway).

# Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions are greatly appreciated.

If you have suggestions for adding or removing projects, feel free to open an issue to discuss it, or directly create a pull request after you edit the README.md file with necessary changes.

Please make sure you check your spelling and grammar.

Please create individual PRs for each suggestion.


# Creating A Pull Request
To create a PR, please use the following steps:
1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

# License 

Distributed under the  AGPL-3.0 License. See LICENSE for more information
