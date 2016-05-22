(ns telegram-bot-lib.handlers
    (:require [clojure.string :as string]))

(defn check_command [n t]
    (let [r (re-pattern (str "/" n "(([@\\s].*)|$)"))]
        (and (not (nil? t)) (not (nil? (re-matches r t))))))

(defn create_command [n f] {
        :pr #(check_command n (get-in % [:message :text]))
        :f f
    })

(defn create_handler [pr f] {
        :pr pr
        :f f
    })

(defn create_inline_query_handler [f] {
        :pr #(not (nil? (get % :inline_query)))
        :f f
    })

(defn parse_command_arguments [s]
    (rest (string/split s #" ")))