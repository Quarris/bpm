package bpm.common.workspace.graph

import bpm.common.memory.Buffer
import bpm.common.property.*
import bpm.common.serial.Serial
import bpm.common.serial.Serialize
import org.joml.Vector4i
import java.util.UUID

data class Node(override val properties: PropertyMap = Property.Object()) : PropertySupplier {


    /**
     * [uid] is a read-only variable of type [UUID] that generates a random UUID
     * using [UUID.randomUUID()] only if not provided by the [properties] map.
     *
     * @property uid The unique identifier generated as a random UUID.
     */
    val uid: UUID by properties delegate { UUID.randomUUID() }

    /**
     * Represents a [name] variable which generates a random UUID as its value using a properties delegate.
     *
     * @property name The property representing the random UUID generated by the properties delegate.
     */
    val name: String by properties delegate { UUID.randomUUID().toString() }

    /**
     * Represents a variable of type [String] delegated by properties delegate.
     *
     * This corresponds to the type of the node. See [NodeTypeMeta]
     *
     * The value of this variable is always "Node".
     */
    val type: String by properties delegate { "Node" }

    /**
     * Represents a variable of icon type [String] delegated by properties delegate.
     * This corresponds to the icon of the node.
     */
    val icon: Int by properties delegate { 0x1F4D6 }

    /**
     * Delegate-Backed Property.
     *
     * @property x The delegated property of type Float.
     *
     * @constructor Creates a delegate-backed property with an initial value of 0.0f.
     */
    var x: Float by properties delegate { 0.0f }

    /**
     * Delegate-Backed Property.
     *
     * @property y The delegated property of type Float.
     *
     * @constructor Creates a delegate-backed property with an initial value of 0.0f.
     */
    var y: Float by properties delegate { 0.0f }

    /**
     * Delegate-Backed Property.
     *
     * @property width The delegated property of type Float.
     *
     * @constructor Creates a delegate-backed property with an initial value of 0.0f.
     */
    var width: Float by properties delegate { 0.0f }

    /**
     * Delegate-Backed Property.
     *
     * @property height The delegated property of type Float.
     *
     * @constructor Creates a delegate-backed property with an initial value of 0.0f.
     */
    var height: Float by properties delegate { 0.0f }

    /**
     * Represents a color value as an integer.
     *
     * @property color The color value represented as an integer.
     */
    var color: Vector4i by properties delegate { Vector4i(30, 30, 30, 255) }

    /**
     * Represents a [selected] variable of type [Boolean] that is false by default.
     *
     * @property selected The boolean value representing whether or not the node is selected.
     */
    var selected: Boolean by properties delegate { false }

    /**
     * Represents a [dragged] variable of type [Boolean] that is false by default.
     *
     * @property dragged The boolean value representing whether or not the node is hovered.
     */
    var dragged: Boolean by properties delegate { false }

    /**
     * Represents a list of property edges, identified by their unique IDs.
     *
     * @property edgeIds The list of property edges identified by their UUIDs.
     */
    var edgeIds: MutableList<Property<UUID>> by properties delegate { mutableListOf() }

    /**
     * Represents a list of property literals.
     *
     * @property values The list of property literals.
     */
    val values: List<PropertyLiteral<*>> by properties delegate { emptyList() }


    /**
     * Sets the value of a property with the given name.
     *
     * @param name the name of the property
     * @param value the value to be set for the property
     * @throws IllegalArgumentException if the name or value is null
     */
    operator fun <T : Property<*>> set(name: String, value: T) {
        properties[name] = value
    }

    /**
     * Returns the property with the specified name.
     *
     * @param name The name of the property to retrieve.
     * @return The property with the specified name.
     */
    operator fun get(name: String): Property<*> {
        return properties[name]
    }

    infix fun getProperty(s: String): Property<*> {
        return properties[s]
    }


    object NodeSerializer : Serialize<Node>(Node::class) {

        /**
         * Deserializes the contents of the Buffer and returns an instance of type T.
         *
         * @return The deserialized object of type T.
         */
        override fun deserialize(buffer: Buffer): Node {
            val properties = Serial.read<PropertyMap>(buffer) ?: error("Failed to read properties from buffer!")
            return Node(properties)
        }

        /**
         * Serializes the provided Node instance into the specified buffer.
         *
         * @param buffer The buffer in which the Node will be serialized.
         * @param value The Node instance to be serialized.
         */
        override fun serialize(buffer: Buffer, value: Node) {
            Serial.write(buffer, value.properties)
        }
    }

}



