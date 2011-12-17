(ns minds.main
  (:use minds.core
        minds.util)
  (:import java.io.File))

(def default-target-dir "./target")
(def default-from-dir "./data")

(defn json-files [from-file]
  (filter #(.. % getName (endsWith ".json")) (file-seq from-file)))

(defn -main [& args]
  (let [fdir (first args)
        tdir (get (vec args) 1)
        from-dir (File. (if (is-empty? fdir) default-from-dir fdir))
        target-dir (File. (if (is-empty? tdir) default-target-dir tdir))
        files (json-files from-dir)]
    (doseq [file files]
      (println file)
      (write-mindmeister-to-freemind file (File. target-dir (str (.getName file) ".mm"))))))