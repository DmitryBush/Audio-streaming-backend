package ohio.rizz.streamingservice.dto;

/**
 * DTO to create artwork
 * @param objectStorageLink Path to get album artwork from s3 storage
 * @param binaryArray An array storing the album cover
 */
public record ArtworkDto(String objectStorageLink, byte[] binaryArray) {
}
