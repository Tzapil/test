(ns telegram-bot-lib.handlers)

(defn check_command [n t]
    (let [r (re-pattern (str "/" n "(([@\\s].*)|$)"))]
        (not (nil? (re-matches r t)))))

(defn create_command [n f] {
        :pr #(check_command n (get-in % [:message :text]))
        :f f
    })