(ns telegram-bot-lib.core
  (:require [telegram-bot-lib.bot :as bot]
            [telegram-bot-lib.helpers :as helpers]
            [telegram-bot-lib.updater :as updater]
            [telegram-bot-lib.handlers :as handlers]
            [telegram-bot-lib.filters :as filters]
            [telegram-bot-lib.inline :as inline]
            [clojure.string :as string])
  (:gen-class))

(def bot-token "***REMOVED***")

(defn echo [data]
    (let [id (get-in data [:message :chat :id])
          text (get-in data [:message :text])]
          (println "ECHO_ANSWER: ")
          (println id)
          (println text)
          (bot/send_message bot-token id text nil "Markdown")))

(defn escape_markdown [])

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
    (handlers/create_command "start" #(bot/send_message bot-token (get-in % [:message :chat :id]) "HI!"))
    (handlers/create_command "help" #(bot/send_message bot-token (get-in % [:message :chat :id]) "HELP!"))
    (handlers/create_inline_query_handler inline_handler)
    (handlers/create_handler filters/text echo)
    ])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (bot/get_me bot-token))
  ;;(println (bot/send_message "***REMOVED***" 53941045 "kokoko"))
  (updater/start_handlers h (updater/start_polling bot-token))
  ;;(.addShutdownHook (Runtime/getRuntime) (Thread. (fn [] (println "Shutting down..."))))
  (updater/idle))
