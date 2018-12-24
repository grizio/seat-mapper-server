declare global {
  namespace JSX {
    interface IntrinsicElements {
      "seat-mapper": {
        "initial-structure"?: string
        version?: string
        onChange?: (value: object) => void
      }
      "seat-mapper-preview": {
        "structure": string
      }
    }
  }
}