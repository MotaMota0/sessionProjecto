﻿# sessionProjecto

Как всё работает вместе
Пользователь открывает http://localhost:8085/:
Spring Security перенаправляет на /login, если не аутентифицирован.
Пользователь вводит user:password в форме /login.
После логина редирект на /, где загружается index.html.
Фронтенд:
index.html запрашивает имя пользователя через /api/username.
Подключается к /ws с помощью SockJS и STOMP, отправляя сессионную куку.
Отправляет сообщение JOIN на /app/chat.addUser.
Бэкенд:
ChatController получает имя пользователя из UserDetails и сохраняет его в сессии.
Сообщения сохраняются в базе через ChatMessageRepository.
WebSocketEventListener обрабатывает отключения, используя имя из сессии.
WebSocket:
Spring Security проверяет аутентификацию для /ws/**.
STOMP-сообщения маршрутизируются через /app (контроллер) и /topic (рассылка).
