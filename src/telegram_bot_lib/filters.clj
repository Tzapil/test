(ns telegram-bot-lib.filters)

(defn acommand [n t]
    (let [r (re-pattern (str "/" n "(([@\\s].*)|$)"))]
        (and (not (nil? t)) (not (nil? (re-matches r t))))))

(defn command [m]
    (and (not (nil? m)) 
         (let [t (get-in m [:message :text])]
            (and (not (nil? t)) (starts-with? t "/")))))
