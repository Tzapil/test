(ns telegram-bot-lib.core
  (:require [telegram-bot-lib.bot :as bot]
            [telegram-bot-lib.helpers :as helpers])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (bot/get_me "***REMOVED***"))
  (println (bot/send_message "***REMOVED***" 53941045 "kokoko"))
  (println "Hello, World!"))
