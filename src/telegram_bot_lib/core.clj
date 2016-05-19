(ns telegram-bot-lib.core
  (:require [telegram-bot-lib.bot :as bot]
            [telegram-bot-lib.helpers :as helpers]
            [telegram-bot-lib.updater :as updater]
            [telegram-bot-lib.handlers :as handlers]
            [telegram-bot-lib.filters :as filters])
  (:gen-class))

(def bot-token "***REMOVED***")

(defn echo [data]
    (println "ECHO_ANSWER: ")
    (println (get-in data [:message :message_id]))
    (bot/send_message bot-token (get-in data [:message :chat :id]) (get-in data [:message :text])))

(def h [
    (handlers/create_command "start" #(bot/send_message bot-token (get-in % [:message :chat :id]) "HI!"))
    (handlers/create_command "help" #(bot/send_message bot-token (get-in % [:message :chat :id]) "HELP!"))
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
