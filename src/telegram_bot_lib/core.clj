(ns telegram-bot-lib.core
  (:require [clojure.core.async :as async]
            [telegram-bot-lib.bot :as bot]
            [telegram-bot-lib.helpers :as helpers]
            [telegram-bot-lib.updater :as updater]
            [telegram-bot-lib.handlers :as handlers]
            [telegram-bot-lib.filters :as filters]
            [telegram-bot-lib.inline :as inline]
            [clojure.string :as string]
            [telegram-bot-lib.emoji :as emoji])
  (:gen-class))

(def bot-token "***REMOVED***")

(defn echo [data]
    (let [id (get-in data [:message :chat :id])
          text (get-in data [:message :text])]
          (println "ECHO_ANSWER: ")
          (println id)
          (println text)
          (bot/send_message bot-token id text nil "Markdown")))

(defn timer [data]
    (let [id (get-in data [:message :chat :id])
          text (get-in data [:message :text])
          args (handlers/parse_command_arguments text)]
          (if (nil? (re-find  #"\d+" (first args)))
              (bot/send_message bot-token id "Argument must be a number!")
              (let [t (helpers/parse_int (first args))]
                (async/go
                    (async/<! (async/timeout t))
                    (bot/send_message bot-token id (str "Beep after " t " ms!")))))))

(defn send_photo [data]
    (let [id (get-in data [:message :chat :id])
          text (get-in data [:message :text])]
          (bot/send_photo bot-token id (clojure.java.io/file "touhou.jpg"))))

(defn inline_handler [data]
    (println "INLINE: ")
    (let [id (get-in data [:inline_query :id])
          iq (get-in data [:inline_query :query])
          results [
            (inline/create_result_article "Caps" (string/upper-case iq))
            (inline/create_result_article "Bold" (str "*" iq "*") nil "Markdown")
            (inline/create_result_article "Italic" (str "_" iq "_") nil "Markdown")
          ]]
        (bot/answer_inline_query bot-token id results)))

(def h [
    (handlers/create_command "start" #(bot/send_message bot-token (get-in % [:message :chat :id]) (str "HI! " emoji/WINKING_FACE)))
    (handlers/create_command "help" #(bot/send_message bot-token (get-in % [:message :chat :id]) "HELP!"))
    (handlers/create_command "set" timer)
    (handlers/create_command "cirno" send_photo)
    (handlers/create_status_handler :new_chat_title #(bot/send_message bot-token (get-in % [:message :chat :id]) "WOW!"))
    (handlers/create_status_handler :left_chat_member #(bot/send_message bot-token (get-in % [:message :chat :id]) "WAIT!"))
    (handlers/create_inline_query_handler inline_handler)
    ;;(handlers/create_handler filters/text echo)
    ])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (bot/get_me bot-token))
  ;;(println (bot/send_message "***REMOVED***" 53941045 "kokoko"))
  (updater/start_handlers h (updater/start_polling bot-token 100 1000 0))
  ;;(updater/start_handlers h (updater/start_webhook bot-token "***REMOVED***.tk" 8443 "hook" "cert.pem" "cert.keystore" "***REMOVED***"))
  (updater/idle))
