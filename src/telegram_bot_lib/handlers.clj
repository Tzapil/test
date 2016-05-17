(ns telegram-bot-lib.handlers
    :require [clojure.core.filters :as filters]))

(defn create_command [n f] {
        :pr #(filters/command n (get-in % [:message :text]))
        :f f
    })