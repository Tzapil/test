(ns telegram-bot-lib.core
  (:require [telegram-bot-lib.bot :as bot]
            [telegram-bot-lib.helpers :as helpers])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (bot/get_me "141043767:AAGOD1ZEAvzNUuii6_Zxy-zydbU2x5z77so"))
  (println (bot/send_message "141043767:AAGOD1ZEAvzNUuii6_Zxy-zydbU2x5z77so" 53941045 "kokoko"))
  (println "Hello, World!"))
