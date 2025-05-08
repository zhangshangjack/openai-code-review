curl -X POST \
        -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsInNpZ25fdHlwZSI6IlNJR04ifQ.eyJhcGlfa2V5IjoiY2EzMzRjNWE0Y2FiNDg0MmFkMTI0YjQ3M2U5N2Y3YTkiLCJleHAiOjE3NDY2ODk2MjAwNTgsInRpbWVzdGFtcCI6MTc0NjY4NzgyMDA2OH0.i7CM4GXk0GL2tiyU2KzxAbca5I8iwjsPWIyuOXEGtuQ" \
        -H "Content-Type: application/json" \
        -H "User-Agent: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" \
        -d '{
          "model":"glm-4",
          "stream": "true",
          "messages": [
              {
                  "role": "user",
                  "content": "1+1"
              }
          ]
        }' \
  https://open.bigmodel.cn/api/paas/v4/chat/completions
