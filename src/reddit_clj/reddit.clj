(ns reddit-clj.reddit
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(def base-url "http://reddit.com/")
(def keys- [:title :url :score :author :subreddit :num_comments
            :permalink :created :thumbnail :over_18])

(defn- get-json
  [full-url]
  (println "GET -> " full-url)
  (-> (client/get full-url)
      (:body)
      (json/parse-string true)
      (:data)
      (:children)))

(defn- extract-keys
  [page-json]
  (->> (map #(get-in % [:data]) page-json)
       (map #(select-keys % keys-))))

(defn get-page-items
  ([]
   (-> (get-json (str base-url ".json"))
       (extract-keys)))
  ([subreddit]
   (-> (get-json (str base-url "r/" subreddit ".json"))
       (extract-keys)))
  ([subreddit remove-nsfw]
   (->> (get-json (str base-url "r/" subreddit ".json"))
        (#(if remove-nsfw
           (filter (fn [x] (not (:over_18 x))) (extract-keys %))
           (extract-keys %)))
        )))
