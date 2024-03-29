(ns bspace-part.core
  (:gen-class)
  (:require [bspace-part.common :refer :all]
            [bspace-part.geometry :refer :all]
            [clojure.pprint :as pp]
            [quil.core :as q]
            [quil.middleware :as m]))

(def segments (disjoint_segments n))


(defn setup
  "Drawing function to get an idea of what the generated segments look like"
  []
  (q/frame-rate 60)
  (q/background 250)
  (q/fill 0)

  (doseq [seg segments]
    (let [[x1 y1 x2 y2] (list_of_points seg)]
      (q/line x1 y1 x2 y2))))


(defn randauto
  [segments]
  (if (empty? segments)
    segments
    (let [chosen (first segments)
          seg_list (rest segments)
          left (filter (partial segment_on_one_side chosen) seg_list)
          right  (filter (partial segment_on_other_side chosen) seg_list)
          intersects (filter (partial line_seg_intersect? chosen) seg_list)]
      {:seg chosen
       :left (->> (map (partial half_segments chosen neg?) intersects)
                  (concat left ,,,)
                  (remove nil? ,,,)
                  (randauto ,,,))
       :right (->> (map (partial half_segments chosen pos?) intersects)
                   (concat right ,,,)
                   (remove nil? ,,,)
                   (randauto ,,,))})))


(q/defsketch bsp-sketch
  :title "Binary Space Partitions"
  :settings #(q/smooth 2)
  :setup setup
  :middleware [m/fun-mode]
  :size [WIDTH HEIGHT])


(defn tree_size
  "Function given to reduce-kv to count the number of segments by
  exploring the tree (a key-value hashap) depth-first, adding +1 to
  the reduce accumulator whenever we encounter a segment in the
  space (a partition)"
  [already key value]
  (if (and (map? value)
           (contains? value :seg))
    (reduce-kv tree_size (inc already) value)
    already))

(defn -main []
  (println "Canvas size: " WIDTH "x" HEIGHT)
  (println "Max segment length:" MAX_SEGMENT_LENGTH)
  (println "Tree size: ")

  (let [tree (randauto segments)]
    (prn (tree_size 0 :root tree))))
