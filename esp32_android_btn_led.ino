#include <WiFi.h>

// Параметры сети Wi-Fi
const char* ssid = "";
const char* password = "";

// Порт для TCP/IP соединения
const uint16_t port = 12345;  // Порт сервера
WiFiServer server(port);

// Пин для светодиода и кнопки
const int ledPin = 5;
const int buttonPin = 4;  // Пин для кнопки (используйте доступный на вашей плате)

bool ledState = false;  // Текущее состояние светодиода
bool lastButtonState = HIGH;  // Предыдущее состояние кнопки (предположим, что кнопка нормально разомкнута)

void setup() {
  // Инициализация последовательного порта для отладки
  Serial.begin(115200);

  // Инициализация пина для светодиода и кнопки
  pinMode(ledPin, OUTPUT);
  pinMode(buttonPin, INPUT_PULLUP);  // Включаем подтяжку к питанию для кнопки
  
  digitalWrite(ledPin, LOW);  // Светодиод выключен по умолчанию

  // Подключение к Wi-Fi сети
  Serial.printf("Подключаемся к сети %s", ssid);
  WiFi.begin(ssid, password);

  // Ожидание подключения к Wi-Fi
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }

  // Вывод IP-адреса после успешного подключения
  Serial.println("");
  Serial.print("Подключено, IP-адрес: ");
  Serial.println(WiFi.localIP());

  // Запуск TCP/IP сервера
  server.begin();
  Serial.printf("Сервер запущен на порту %d\n", port);
}

void loop() {
  // Проверка на входящие соединения
  WiFiClient client = server.available();

  if (client) {
    Serial.println("Новый клиент подключен.");
    
    while (client.connected()) {
      // Проверка состояния кнопки
      bool currentButtonState = digitalRead(buttonPin);
      
      // Если кнопка нажата (переход из HIGH в LOW)
      if (lastButtonState == HIGH && currentButtonState == LOW) {
        // Меняем состояние светодиода
        ledState = !ledState;
        digitalWrite(ledPin, ledState ? HIGH : LOW);

        // Отправляем состояние на Android
        if (ledState) {
          client.println("LED ON");
          Serial.println("Отправлено: LED ON");
        } else {
          client.println("LED OFF");
          Serial.println("Отправлено: LED OFF");
        }
        
        // Даем время для debounce (устранения дребезга)
        delay(200);
      }
      
      // Обновляем предыдущее состояние кнопки
      lastButtonState = currentButtonState;
      
      // Если доступны данные от клиента (на случай, если потребуется получать данные от Android)
      if (client.available()) {
        String request = client.readStringUntil('\n');
        Serial.print("Получено сообщение: ");
        Serial.println(request);

        // Изменяем состояние светодиода в зависимости от полученного сообщения
        if (request == "LED ON") {
          ledState = true;
          digitalWrite(ledPin, HIGH);
          Serial.println("Светодиод включен по команде от Android.");
        } 
        else if (request == "LED OFF") {
          ledState = false;
          digitalWrite(ledPin, LOW);
          Serial.println("Светодиод выключен по команде от Android.");
        }
      }
    }

    // Отключение клиента после завершения общения
    client.stop();
    Serial.println("Клиент отключён.");
  }
}
