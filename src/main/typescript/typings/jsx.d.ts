import {SeatMapperChangeEvent} from "seat-mapper"

declare global {
  namespace JSX {
    interface IntrinsicElements {
      "seat-mapper": {
        "initial-structure"?: string
        version?: string
        onChange?: (event: SeatMapperChangeEvent) => void
      }
      "seat-mapper-preview": {
        "structure": string
      }
    }
  }
}