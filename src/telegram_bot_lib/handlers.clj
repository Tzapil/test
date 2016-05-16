(ns telegram-bot-lib.handlers)

(defn check_command [n t]
    (println "REG")
    (println n)
    (println t)
    (let [r (re-pattern (str "/" n "(([@\\s].*)|$)"))]
        (not (nil? (re-matches r t)))))

(defn add_command [n f] {
        :pr #((println (str "SHASAH: " %)) (check_command n (get-in % [:message :text])))
        :f f
    })