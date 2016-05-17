(ns telegram-bot-lib.filters)

(defn acommand [n t]
    (let [r (re-pattern (str "/" n "(([@\\s].*)|$)"))]
        (and (not (nil? t)) (not (nil? (re-matches r t))))))

(defn command [t]
    (not (nil? t)))
