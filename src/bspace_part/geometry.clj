(ns bspace-part.geometry
  (:require [bspace-part.common :refer :all]))

(defn sub-point
  "Point subtraction"
  [p1 p2]
  {:x (- (:x p2) (:x p1))
   :y (- (:y p2) (:y p1))})

(defn cross-p
  "Cross product of 2D vectors"
  [v1 v2]
  (- (* (:x v1) (:y v2))
     (* (:y v1) (:x v2))))

(defn seg_point_position
  "Returns a negative number, a positive number or 0 if the point is on
  one side of the line supporting the segment, the other side, or on
  it"
  [s1 p1]
  (cross-p (sub-point (:b s1) (:a s1))
           (sub-point p1 (:a s1))))

(defn segment_on_one_side
  "Returns True if the segment is strictly aside of the line"
  [l1 s2]
  (and (neg? (seg_point_position l1 (:a s2)))
       (neg? (seg_point_position l1 (:b s2)))))

(defn segment_on_other_side
  "Returns True if the segment is strictly aside of the line"
  [l1 s2]
  (and (pos? (seg_point_position l1 (:a s2)))
       (pos? (seg_point_position l1 (:b s2)))))

(defn line_seg_intersect?
  "Returns a boolean that says whether the supporting line l1 intersects
  s2"
  [l1 s2]
  (not (or (segment_on_one_side l1 s2)
           (segment_on_other_side l1 s2))))

(defn points->line
  "Returns the 2 given points as the line equation Ax + By = C"
  [p1 p2]
  {:A (- (:y p1) (:y p2))
   :B (- (:x p2) (:x p1))
   :C (- (- (* (:x p1) (:y p2))
            (* (:x p2) (:y p1))))})

(defn line_seg_intersection
  "Returns the point at which the supporting line of one segment
  intersects with another segment (or its line)"
  [l1 l2]
  (let [det (- (* (:A l1) (:B l2)) (* (:B l1) (:A l2)))
        dx (- (* (:C l1) (:B l2)) (* (:B l1) (:C l2)))
        dy (- (* (:A l1) (:C l2)) (* (:C l1) (:A l2)))]
    {:x (/ dx det) :y (/ dy det)}))

(defn seg_intersect?
  "Returns a boolean that says if the two segments intersect or not"
  [s1 s2]
  (and (line_seg_intersect? s1 s2)
       (line_seg_intersect? s2 s1)))

(defn random_epsilon
  "Random integer in the range [-MAX_SEGMENT_LENGTH,
  MAX_SEGMENT_LENGTH], to make the segments a bit different from each
  other"
  []
  (.nextInt rand_number_gen (- MAX_SEGMENT_LENGTH) MAX_SEGMENT_LENGTH))

(defn random_segment
  "Return a random segment of max size MAX_SEGMENT_LENGTH."
  []
  (let [x (rand-int WIDTH)
        y (rand-int HEIGHT)]
    {:a {:x x :y y}
     :b {:x (+ x (random_epsilon)) :y (+ y (random_epsilon))}}))

(defn disjoint_segments
  "Returns n disjoint segments in an array. The control counter i keeps
  track of the generated _disjoint_ segments so far"
  [n]
  (loop [i 0
         arr []]
    (if (= n i)
      ;; we've generated n disjoints segments, return arr
      arr
      (let [seg (random_segment)]
        (if (some (partial seg_intersect? seg) arr)
          ;; if some segment intersects with any other in the array,
          ;; do nothing, keep looping <- dangerous !
          (recur i arr)
          ;; if segment ok, conj it to the list
          (recur (inc i) (conj arr seg)))))))

(defn list_of_points
  "Converts a segment structured as a map to a point as an array of
  positional coordinates (for quil) "
  [s]
  [(:x (:a s)) (:y (:a s)) (:x (:b s)) (:y (:b s))])
