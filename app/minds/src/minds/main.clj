(ns minds.main
  (:use minds.core
        [minds.util :only (empty-else)])
  (:import java.io.File))

(def default-target-dir "./target")
(def default-from-dir "./data")

(defn json-files [from-file]
  (filter #(.. % getName (endsWith ".json")) (file-seq from-file)))

(defn -main [& [fdir tdir]]
  (let [from-dir (File. (empty-else fdir default-from-dir))
        target-dir (File. (empty-else tdir default-target-dir))
        files (json-files from-dir)]
    (doseq [file files]
      (println (.getName file))
      (write-mindmeister-to-freemind file (File. target-dir (str (.getName file) ".mm"))))))