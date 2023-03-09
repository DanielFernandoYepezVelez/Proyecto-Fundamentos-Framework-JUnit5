package com.example.junit.app.models;

import com.example.junit.app.exceptions.MoneyInsufficientException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class AccountTest {
    static Account account;
    private TestInfo testInfo;
    private TestReporter testReporter;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicianlizando la clase del test");
    }

    @BeforeEach
    void initMethodTest(TestInfo testInfo, TestReporter testReporter) {
        // INYECCIÓN DE DEPENDENCIAS (TestInfo - TestReporter)
        // Lo Implemento En Este Punto, Para Que Se Ejecute De Forma Global
        System.out.println(" Ejecutando: " + testInfo.getDisplayName() + " " +
                testInfo.getTestMethod().get().getName() + " Con Las Etiquetas: " + testInfo.getTags());

        testReporter.publishEntry(" Ejecutando: " + testInfo.getDisplayName() + " " +
                testInfo.getTestMethod().get().getName() + " Con Las Etiquetas: " + testInfo.getTags());

        System.out.println("Método Que Se Inicia Antes, Se Ejecuta Por Cada Método!!");
        // 1. Lo Objetos Que Hacen Parte De Mi Escenario
        account = new Account();

        ////////////////////////////////
        this.testInfo = testInfo;
        this.testReporter = testReporter;
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando El Test De Prueba Por Cada Invocación. @TEST");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando la clase del test");
    }

    @Nested
    @Tag("AccountNameTAG")
    @DisplayName("Probando El Test Para El Nombre De Las Cuentas")
    class AccountNameTest {
        @Test
        @DisplayName("Test Account Current Name")
        void returnNameAccountSetPersonTest(TestInfo testInfo, TestReporter testReporter) {
            // INYECCIÓN DE DEPENDENCIAS (TestInfo - TestReporter)
            /* System.out.println(" Ejecutando: " + testInfo.getDisplayName() + " " +
                    testInfo.getTestMethod().get().getName() + " Con Las Etiquetas: " + testInfo.getTags()); */

            // 1. Lo Objetos Que Hacen Parte De Mi Escenario
            //Account account = new Account();

            // 2. LLamado A Los Metodos
            account.setPerson("Daniel Fernando Yepez Vélez");

            /* Datos Esperados vs Realidad De Mi Test */
            String expect = "Daniel Fernando Yepez Vélez";
            String actual = account.getPerson();

            // 3. Afirmar O Verificar El Test
            Assertions.assertEquals(expect, actual, () -> "Name Person Account Is Not Equals!");
        }

        @Test
        @DisplayName("Test Account Current Name Construct")
        void returnNameAccountPersonConstructorTest() {
            testReporter.publishEntry(testInfo.getTags().toString());

            if (testInfo.getTags().contains("AccountNameTAG")) {
                testReporter.publishEntry("Hacer Algo Con El Nombre De La Etiqueta");
            }

            // 1. Los Objetos Que Van A Participar En Mi Escenario / 2. LLamado De Los Métodos
            BigDecimal balanceValue = new BigDecimal("1000.123456");
            AccountTest.account = new Account("Fernando", balanceValue);

            // 3. Afirmar O Verificar El Test (Se Puede Más De Una Afirmación)
            Assertions.assertNotNull(account.getPerson(), "Name Person Account Is Null!");
            Assertions.assertEquals("Fernando", account.getPerson(), "Name Person Account Is Not Equals!");
            Assertions.assertTrue(account.getPerson().equals("Fernando"), "Name Person Account Is Not True!");
        }
    }

    @Nested
    @Tag("AccountBalanceTAG")
    @DisplayName("Probando El Test Para El Saldo De Las Cuentas")
    class AccountBalanceTest {
        @Test
        @DisplayName("Test Account Balance")
        void returnValueBalanceAccountSetBalanceTest() {
            // 1. Los Objetos Que Van A Interactuar En Mis Escenario
            //Account account = new Account();

            // 2. LLamado De Los Métodos
            account.setBalance(new BigDecimal("23.4556"));

            // 3.Afirmar El Test
            Assertions.assertEquals(new BigDecimal("23.4556"), account.getBalance());
        }

        @Test
        @DisplayName("Test Account Balance Constructor")
        void returnValueBalanceAccountConstructorTest() {
            // 1. Los Objetos Que Van Hacer Parte De Mi Escenario / 2. Llamado A Los Métodos
            AccountTest.account = new Account("Laura Camila", new BigDecimal("1000.1234"));

            // 3. Afirmar Los Test
            Assertions.assertNotNull(account.getBalance());
            Assertions.assertEquals(1000.1234, account.getBalance().doubleValue());
            Assertions.assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0);
            Assertions.assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
        }
    }

    @Nested
    class AccountOperationsTest {
        @Test
        void returnReferenceAccountTest() {
            // 1. Los Objetos Que Van A Participar En Mi Escenario
            AccountTest.account = new Account("Jhon Do", new BigDecimal("88.9997"));
            Account account2 = new Account("Jhon Do", new BigDecimal("88.9997"));

            // 3. Afirmar Los Test
            //Assertions.assertNotEquals(account2, account);
            Assertions.assertEquals(account2, account);
        }

        @Tag("AccountMoneyTransfer")
        @Test
        void returnDebitAccountPositiveBalanceTest() {
            // 1. Los Objetos Que Hacen Parte De Mi Escenario
            AccountTest.account = new Account("Esteban Marín", new BigDecimal("1000.12345"));

            // 2. Llamado A Los Métodos
            account.debito(new BigDecimal(100));

            // 3. Afirmar El Test
            Assertions.assertNotNull(account.getBalance());
            Assertions.assertEquals(900, account.getBalance().intValue());
            Assertions.assertEquals("900.12345", account.getBalance().toPlainString());
        }

        @Tag("AccountMoneyTransfer")
        @Test
        void returnCreditAccountPositiveBalanceTest() {
            // 1. Los Objetos Que Hacen Parte De Mi Escenario
            AccountTest.account = new Account("Carlos Pulgarin", new BigDecimal("1000.4567"));

            // 2. LLamado De Los Métodos
            account.credito(new BigDecimal(100));

            // 3. Afirmar El Test
            Assertions.assertNotNull(account.getBalance());
            Assertions.assertEquals(1100, account.getBalance().intValue());
            Assertions.assertEquals("1100.4567", account.getBalance().toPlainString());
        }

        @Test
        @Tag("AccountMoneyTransfer")
        @Tag("AccountMoneyTransferBANK")
        void returnMoneyTransferAccountOtherTest() {
            // 1. Objetos Que Participan En Mi Escenario
            Bank bank = new Bank();
            Account accountJhonDo = new Account("Jhon Do", new BigDecimal("2500"));
            Account accountDaniel = new Account("Daniel", new BigDecimal("1500.8989"));

            // 2. LLamada De Los Métodos
            bank.setName("Banco De La Republica");
            bank.transfer(accountDaniel, accountJhonDo, new BigDecimal(500));

            // 3. Afirmar Test
            assertEquals("1000.8989", accountDaniel.getBalance().toPlainString());
            assertEquals("3000", accountJhonDo.getBalance().toPlainString());
        }
    }

    /* El Trabajo De Este Test, Es Comprobar Si El Saldo No Es Menor Que El Monto Que Va A Retirar Del Banco */
    @Tag("AccountMoneyTransfer")
    @Tag("Error")
    @Test
    void returnInsufficientMoneyAccountExceptionTest() {
        // 1. Objetos Que Van A Participar En Mi Escenario
        this.account = new Account("Camila Fernanda Garzón", new BigDecimal("1000.12345"));

        // 3. Afirmar Los Test
        // (assertThrows) Retorna Una Exception
        Exception exception = Assertions.assertThrows(MoneyInsufficientException.class, () -> {
            // 2. LLamado De Los Métodos
            account.debito(new BigDecimal(1500));
        });

        // 2. LLamado De Los Métodos
        String expect = "Money Insufficient";
        String actual = exception.getMessage();

        // 3. Afirmar Los Test
        Assertions.assertEquals(expect, actual);
    }

    @Test
    @Disabled
    void returnBankAccountRelationshipOneTest() {
        Assertions.fail();

        // 1. Los Objetos Que Hacen Parte De Mi Escenario
        Bank bank = new Bank();
        Account accountDaniel = new Account("Daniel", new BigDecimal("2500"));
        Account accountFernando = new Account("Fernando", new BigDecimal("1500.8989"));

        // 2. LLamado De Los Métodos
        bank.setName("Banco Del Estado");
        bank.addAccount(accountDaniel);
        bank.addAccount(accountFernando);
        bank.transfer(accountFernando, accountDaniel, new BigDecimal(500));

        // 3. Afirmar Los Tests
        Assertions.assertEquals("1000.8989", accountFernando.getBalance().toPlainString());
        Assertions.assertEquals("3000", accountDaniel.getBalance().toPlainString());
        assertEquals(2, bank.getAccounts().size());
        assertEquals("Banco Del Estado", accountDaniel.getBank().getName());

        assertEquals("Daniel", bank.getAccounts()
                                            .stream()
                                            .filter(account -> account.getPerson().equals("Daniel"))
                                            .findFirst()
                                            .get().getPerson());

        assertTrue(bank.getAccounts()
                .stream()
                .filter(account -> account.getPerson().equals("Daniel"))
                .findFirst()
                .isPresent());

        assertTrue(bank.getAccounts()
                .stream()
                .anyMatch(account -> account.getPerson().equals("Fernando")));
    }

    @Test
    @Tag("AccountMoneyTransfer")
    @Tag("AccountMoneyTransferBANK")
    void returnBankAccountRelationshipTest() {
        // 1. Los Objetos Que Hacen Parte De Mi Escenario
        Bank bank = new Bank();
        Account accountDaniel = new Account("Daniel", new BigDecimal("2500"));
        Account accountFernando = new Account("Fernando", new BigDecimal("1500.8989"));

        // 2. LLamado De Los Métodos
        bank.setName("Banco Del Estado");
        bank.addAccount(accountDaniel);
        bank.addAccount(accountFernando);
        bank.transfer(accountFernando, accountDaniel, new BigDecimal(500));

        // 3. Afirmar Los Tests (Assert All)
        assertAll(() -> Assertions.assertEquals("1000.8989", accountFernando.getBalance().toPlainString()),
                () -> Assertions.assertEquals("3000", accountDaniel.getBalance().toPlainString()),
                () -> assertEquals(2, bank.getAccounts().size(), () -> "Message With Lambda Function"),
                () -> assertEquals("Banco Del Estado", accountDaniel.getBank().getName()),
                () -> {
                        assertEquals("Daniel", bank.getAccounts()
                                .stream()
                                .filter(account -> account.getPerson().equals("Daniel"))
                                .findFirst()
                                .get().getPerson());
                        },
                () -> {
                        assertTrue(bank.getAccounts()
                                .stream()
                                .filter(account -> account.getPerson().equals("Daniel"))
                                .findFirst()
                                .isPresent());
                        },
                () -> {
                        assertTrue(bank.getAccounts()
                                .stream()
                                .anyMatch(account -> account.getPerson().equals("Fernando")));
                }
        );
    }

    @Nested
    class OperativeSystemTest {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void enviromentOnlyWindowsTest() { }

        @Test
        @DisplayName("Disabled Test Operating System Desabilitado")
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void enviromentOnlyLinuxMacTest() { }
    }

    @Nested
    class JavaVersionTest {
        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void enviromentOnlyJdk8Test() { }

        @Test
        @EnabledOnJre(JRE.JAVA_19)
        void enviromentOnlyJdk19Test() { }

        @Test
        @DisabledOnJre(JRE.JAVA_15)
        void NOenviromentOnlyJdk15Test() { }
    }

    @Nested
    class SystemPropertiesTest {
        @Test
        void printSystemJsonProperties() {
            Properties properties = System.getProperties();
            properties.forEach((key, value) -> System.out.println(key + " : " + value));
        }

        @Test
        //@EnabledIfSystemProperties(named = "java.version", matches = "15.0.1")
        @EnabledIfSystemProperty(named = "java.version", matches = ".*15.*")
        void JavaVersion15Test() { }

        @Test
        @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void only64Test() { }

        @Test
        @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void only32Test() { }

        @Test
        @EnabledIfSystemProperty(named = "user.name", matches = "dfyepes")
        void usernameTest() { }

        @Test
        @EnabledIfSystemProperty(named = "ENV", matches = "dev")
        void devTest() { }
    }

    @Nested
    class EnvironmentVariablesTest {
        @Test
        void printEnvioromentVariables() {
            Map<String, String> getEnv = System.getenv();
            getEnv.forEach((k, v) -> System.out.println(k + " = " + v));
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "HOMEPATH", matches = ".*dfyepes.*")
        void homePathTest() { }

        @Test
        @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "8")
        void processorsTest() { }

        @Test
        @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
        void envTest() { }

        @Test
        @DisabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "prod")
        void disabledProductionEnvTest() { }
    }

    @Test
    @DisplayName("Test Account Balance Constructor ASSUMPTIONS DEV!")
    void returnValueBalanceAccountConstructorDevTest() {
        // 1. Los Objetos Que Van Hacer Parte De Mi Escenario / 2. Llamado A Los Métodos
        this.account = new Account("Laura Camila", new BigDecimal("1000.1234"));

        boolean isDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(isDev);

        // 3. Afirmar Los Test
        Assertions.assertNotNull(account.getBalance());
        Assertions.assertEquals(1000.1234, account.getBalance().doubleValue());
        Assertions.assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0);
        Assertions.assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Test Account Balance Constructor ASSUMPTIONS DEV SEGUNDA PARTE!")
    void returnValueBalanceAccountConstructorDevSegundaParteTest() {
        // 1. Los Objetos Que Van Hacer Parte De Mi Escenario / 2. Llamado A Los Métodos
        this.account = new Account("Laura Camila", new BigDecimal("1000.1234"));

        boolean isDev = "dev".equals(System.getProperty("ENV"));
        assumingThat(isDev, () -> {
            // 3. Afirmar Los Test
            Assertions.assertNotNull(account.getBalance());
            Assertions.assertEquals(1000.1234, account.getBalance().doubleValue());
        });

        Assertions.assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0);
        Assertions.assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
    }

    @DisplayName("Probando Debito Cuenta Repetir")
    @RepeatedTest(value = 5, name = "{displayName} Repeticion Numero {currentRepetition} De {totalRepetitions}")
    void returnRepetedDebitAccountPositiveBalanceTest(RepetitionInfo info) {
        if (info.getCurrentRepetition() == 3) {
            System.out.println("Hola Mundo DESDE LA 3.");
        } else {
            System.out.println("Hola Mundo NO ESTAMOS EN LA 3.");
        }

        // 1. Los Objetos Que Hacen Parte De Mi Escenario
        AccountTest.account = new Account("Esteban Marín", new BigDecimal("1000.12345"));

        // 2. Llamado A Los Métodos
        account.debito(new BigDecimal(100));

        // 3. Afirmar El Test
        Assertions.assertNotNull(account.getBalance());
        Assertions.assertEquals(900, account.getBalance().intValue());
        Assertions.assertEquals("900.12345", account.getBalance().toPlainString());
    }

    @Tag("param")
    @Nested
    class classParameterizedTest {
        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @ValueSource(strings = {"100", "200", "300", "500", "700", "1000"})
            //@ValueSource(doubles = {100, 200, 300, 500, 700, 1000})
        void parameterizedTestDebitAccountPositiveBalanceValueSourceTest(String monto) {
            // 1. Los Objetos Que Hacen Parte De Mi Escenario
            AccountTest.account = new Account("Esteban Marín", new BigDecimal("1000.12345"));

            // 2. Llamado A Los Métodos
            account.debito(new BigDecimal(monto));

            // 3. Afirmar El Test
            Assertions.assertNotNull(account.getBalance());
            Assertions.assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @CsvSource({"1,100", "2,200", "3,300", "4,500", "5,700", "6,1000"})
        void parameterizedTestDebitAccountPositiveCsvSourceBalanceTest(String index, String monto) {
            System.out.println(index + " -> " + monto);

            // 1. Los Objetos Que Hacen Parte De Mi Escenario
            AccountTest.account = new Account("Esteban Marín", new BigDecimal("1000.12345"));

            // 2. Llamado A Los Métodos
            account.debito(new BigDecimal(monto));

            // 3. Afirmar El Test
            Assertions.assertNotNull(account.getBalance());
            Assertions.assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @CsvFileSource(resources = "/data.csv")
        void parameterizedTestDebitAccountPositiveCsvFileSourceBalanceTest(String monto) {
            // 1. Los Objetos Que Hacen Parte De Mi Escenario
            AccountTest.account = new Account("Esteban Marín", new BigDecimal("1000.12345"));

            // 2. Llamado A Los Métodos
            account.debito(new BigDecimal(monto));

            // 3. Afirmar El Test
            Assertions.assertNotNull(account.getBalance());
            Assertions.assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @CsvSource({"200,100,Jhon,Andres", "250,200,Carlos,Mario", "300,300,Gladys,Maria", "510,500,Pepa,Pig", "750,700,Lucas,Luca", "1000,1000,Cata,cata"})
        void parameterizedTestDebitAccountPositiveCsvSource2BalanceTest(String balance, String amount, String expected, String actual) {
            System.out.println(balance + " -> " + amount);

            // 1. Los Objetos Que Hacen Parte De Mi Escenario
            AccountTest.account = new Account("Esteban Marín", new BigDecimal("1000.12345"));

            // 2. Llamado A Los Métodos
            account.setBalance(new BigDecimal(balance));
            account.debito(new BigDecimal(amount));
            account.setPerson(actual);

            // 3. Afirmar El Test
            Assertions.assertNotNull(account.getBalance());
            Assertions.assertNotNull(account.getPerson());
            assertEquals(expected, actual);
            Assertions.assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @CsvFileSource(resources = "/data2.csv")
        void parameterizedTestDebitAccountPositiveCsvFileSource2BalanceTest(String balance, String amount, String expected, String actual) {
            // 1. Los Objetos Que Hacen Parte De Mi Escenario
            AccountTest.account = new Account("Esteban Marín", new BigDecimal("1000.12345"));

            // 2. Llamado A Los Métodos
            account.setBalance(new BigDecimal(balance));
            account.debito(new BigDecimal(amount));
            account.setPerson(actual);

            // 3. Afirmar El Test
            Assertions.assertNotNull(account.getBalance());
            Assertions.assertNotNull(account.getPerson());

            assertEquals(expected, actual);
            Assertions.assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
        }
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @MethodSource("listMont")
    void parameterizedTestDebitAccountPositiveMethodSourceBalanceTest(String monto) {
        // 1. Los Objetos Que Hacen Parte De Mi Escenario
        AccountTest.account = new Account("Esteban Marín", new BigDecimal("1000.12345"));

        // 2. Llamado A Los Métodos
        account.debito(new BigDecimal(monto));

        // 3. Afirmar El Test
        Assertions.assertNotNull(account.getBalance());
        Assertions.assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
    }

    private static List<String> listMont() {
        return Arrays.asList("100", "200", "300", "500", "700", "1000");
    }

    @Nested
    @Tag("Timeout")
    class exampleTomeOutTest {
        @Test
        @Timeout(2)
        void timeoutTest() throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
        }

        @Test
        @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
        void timeoutDosTest() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(1000);
        }

        @Test
        void timeoutAssertionsTest() {
            assertTimeout(Duration.ofSeconds(5), () -> {
                TimeUnit.MILLISECONDS.sleep(4000);
            });
        }
    }
}
