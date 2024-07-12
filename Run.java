    /**
     * Represents a specific run for a runner, each run is a node in time tree of the runner.
     */
    public class Run {
        private float time; // Key
        private Run leftChild;
        private Run middleChild;
        private Run rightChild;
        private Run parent;
        private float minTime;

        /**
         * Constructs a run with default values.
         */
        public Run() {
            this(null, null, null, null, Float.MAX_VALUE);
        }

        /**
         * Constructs a run with specified values.
         *
         * @param parent      The parent of this run.
         * @param leftChild   The left child of this run.
         * @param middleChild The middle child of this run.
         * @param rightChild  The right child of this run (always null in Two-Three Tree).
         * @param time        The time associated with this run.
         */
        public Run(Run parent, Run leftChild, Run middleChild, Run rightChild, float time) {
            this.parent = parent;
            this.leftChild = leftChild;
            this.middleChild = middleChild;
            this.rightChild = rightChild; // In TwoThreeTree always null
            setTime(time);
            this.minTime = time;
        }

        /**
         * Constructs a run with specified values and no right child.
         *
         * @param parent      The parent of this run.
         * @param leftChild   The left child of this run.
         * @param middleChild The middle child of this run.
         * @param time        The time associated with this run.
         */
        public Run(Run parent, Run leftChild, Run middleChild, float time) {
            this(parent, leftChild, middleChild, null, time);
        }

        /**
         * Constructs a run with specified values and no children.
         *
         * @param parent The parent of this run.
         * @param time   The time associated with this run.
         */
        public Run(Run parent, float time) {
            this(parent, null, null, null, time);
        }

        /**
         * Constructs a run with specified time and no parent or children.
         *
         * @param time The time associated with this run.
         */
        public Run(float time) {
            this(null, null, null, null, time);
        }

        /**
         * Gets the parent of this run.
         *
         * @return The parent run.
         */
        public Run getParent() {
            return parent;
        }

        /**
         * Sets the parent of this run.
         *
         * @param parent The parent run.
         */
        public void setParent(Run parent) {
            this.parent = parent;
        }

        /**
         * Gets the left child of this run.
         *
         * @return The left child run.
         */
        public Run getLeftChild() {
            return leftChild;
        }

        /**
         * Sets the left child of this run.
         *
         * @param leftChild The left child run.
         */
        public void setLeftChild(Run leftChild) {
            this.leftChild = leftChild;
        }

        /**
         * Gets the middle child of this run.
         *
         * @return The middle child run.
         */
        public Run getMiddleChild() {
            return middleChild;
        }

        /**
         * Sets the middle child of this run.
         *
         * @param middleChild The middle child run.
         */
        public void setMiddleChild(Run middleChild) {
            this.middleChild = middleChild;
        }

        /**
         * Gets the right child of this run.
         *
         * @return The right child run.
         */
        public Run getRightChild() {
            return rightChild;
        }

        /**
         * Sets the right child of this run.
         *
         * @param rightChild The right child run.
         */
        public void setRightChild(Run rightChild) {
            this.rightChild = rightChild;
        }

        /**
         * Gets the time associated with this run.
         *
         * @return The time.
         */
        public float getTime() {
            return this.time;
        }

        /**
         * Sets the time associated with this run.
         *
         * @param time The time.
         * @throws IllegalArgumentException If time is negative.
         */
        public void setTime(float time) {
            if (time <= 0) {
                throw new IllegalArgumentException();
            } else {
                this.time = time;
            }
        }

        /**
         * Gets the minimum time among this run and its descendants.
         *
         * @return The minimum time.
         */
        public float getMin() {
            return this.minTime;
        }

        /**
         * Sets the minimum time among this run and its descendants.
         *
         * @param newMin The new minimum time.
         */
        public void setMin(float newMin) {
            this.minTime = newMin;
        }
    }
