export function generateKey(): string {
  const allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  const length = 50
  let buffer = ""
  for (let i = 0 ; i < length ; i++) buffer += allowedCharacters.charAt(Math.random() * allowedCharacters.length)
  return buffer
}