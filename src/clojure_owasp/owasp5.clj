(ns clojure_owasp.owasp5)

(def lyrics { :geni-e-o-zepelim "resources/geni-e-o-zepelim"})

(defn get-lyric [lyric-name]
  (->> lyric-name
        keyword
       (get lyrics)
       slurp))

;(println (get-lyric "geni-e-o-zepelim"))
;(println (get-lyric "../src/clojure_owasp/core.clj"))

(def permissions {"1314-ABCD" (set [687432])
                  "1515-112D" (set [132421, 175])})

(defn user-can-read-article? [user-id news-id]
  (println "Checking" user-id news-id)
  (-> permissions
      (get user-id)
      (get news-id)))

;(println (user-can-read-article? "1314-ABCD" 687432))
;(println (user-can-read-article? "1314-ABCD" 175))

; /news/owasp-new-report-is-out ==>  687432
; /news/owasp-old-report-is-updated   ==> 175

; nao estou keywords pois eh uma simulacao de banco
(defn slug-to-id [news-slug]
  (let [db {"/news/owasp-new-report-is-out" 687432
            "/news/owasp-old-report-is-updated" 175
            "/news/old" 132421}]
    (get db news-slug)))

;(println (slug-to-id "/news/owasp-new-report-is-out"))

(defn update-article! [news-id]
  (str "updated " news-id))

(defn edit-news [news-slug query-params session-params]
  (if-let [user-id (:user-id session-params)]
    (if-let [news-id (slug-to-id news-slug)]
      (if (user-can-read-article? user-id news-id)
        (update-article! (:news-id query-params))
        (println "Can not read this news " news-slug news-id)))
    ))
;
;(println
;  (edit-news "/news/owasp-new-report-is-out" {:news-id 687432} {:user-id "1314-ABCD"}))
;
;
;(println
;  (edit-news "/news/owasp-new-report-is-out" {:news-id 175} {:user-id "1314-ABCD"}))
;



(defn edit-news [news-slug all-params]
  (if-let [user-id (:user-id all-params)]
    (if-let [news-id (:news-id all-params)]
      (if (user-can-read-article? user-id news-id)
        (update-article! news-id)
        (println "Can not read this news " news-slug news-id)))
    ))


;(println
;  (edit-news "/news/owasp-new-report-is-out" {:news-id 175, :user-id "1314-ABCD"}))
;

(defn edit-news [news-slug query-params session-params]
  (if-let [user-id (:user-id session-params)]
    (if-let [news-id (:news-id query-params)]
      (if (user-can-read-article? user-id news-id)
        (update-article! news-id)
        (println "Can not read this news " news-slug news-id)))
    ))


(println
  (edit-news "/news/owasp-new-report-is-out" {:news-id 687432} {:user-id "1314-ABCD"}))


(println
  (edit-news "/news/owasp-new-report-is-out" {:news-id 175} {:user-id "1314-ABCD"}))














