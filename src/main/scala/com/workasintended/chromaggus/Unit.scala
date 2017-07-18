//package com.workasintended.chromaggus
//
//import com.badlogic.gdx.graphics.g2d.Batch
//import com.badlogic.gdx.graphics.g2d.BitmapFont
//import com.badlogic.gdx.graphics.g2d.Sprite
//import com.badlogic.gdx.scenes.scene2d._
//import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
//import com.workasintended.chromaggus.ability.Ability
//import com.workasintended.chromaggus.ai.AiComponent
//import com.workasintended.chromaggus.event.DevelopCityEvent
//import com.workasintended.chromaggus.order.Idle
//import com.workasintended.chromaggus.order.Order
//import com.workasintended.chromaggus.unitcomponent._
//
//
//class Unit() extends Group {
//  private val abilities = new Array[Ability](9)
//  var radius: Float = 32
//  var speed: Float = 32
//  private var faction: Faction = _
//  var highlight: Sprite = _
//  private var sprite: Sprite = _
//  private val highlighted = false
//  private var font: BitmapFont = _
//  var ai: AiComponent = _
//  var combat: CombatComponent = _
//  var city: CityComponent = _
//  var movement: MovementComponent = _
//  var renderer: CharacterRendererComponent = _
//  var development: DevelopmentComponent = _
//  var inventory: CityArmory.Inventory = _
//  private var abilityComponent: AbilityComponent = _
//  private var order: Order = new Idle
//  var currentGrid = null
//
//  def getSprite: Sprite = sprite
//
//  def setSprite(sprite: Sprite) = {
//    this.sprite = sprite
//    this.setSize(sprite.getWidth, sprite.getHeight)
//    //this.setOrigin(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
//  }
//
//  def getFont: BitmapFont = font
//
//  def setFont(font: BitmapFont) = this.font = font
//
//  override def act(delta: Float): scala.Unit = {
//    if (this.dead) {
//      Service.eventQueue.enqueue(new Event(EventName.UNIT_DIED, this))
//      return
//    }
//    super.act(delta)
//    if (this.city != null) this.city.update(delta)
//    if (this.ai != null) this.ai.update(delta)
//    if (this.combat != null) this.combat.update(delta)
//    if (this.order != null) this.order.update(delta)
//    if (this.development != null) this.development.update(delta)
//  }
//
//
//  override def draw(batch: Batch, parentAlpha: Float) = {
//    if (this.renderer != null) renderer.draw(batch, parentAlpha)
//    if (this.sprite != null) batch.draw(sprite, this.getX, this.getY, this.getOriginX, this.getOriginY, this.getWidth, this.getHeight, this.getScaleX, this.getScaleY, this.getRotation)
//    if (font != null && combat != null) {
//      val cooldown = if (combat.getPrimaryAbility == null) 0f
//      else combat.getPrimaryAbility.getCooldownProgress
//      val cast = if (combat.getPrimaryAbility == null) 0f
//      else combat.getPrimaryAbility.getCooldownProgress
//      val state = s"${combat.getHp()}/${combat.getMaxHp()}" //String.format("%s/%s, %.0f, %.0f", combat.getHp, combat.getMaxHp, cooldown, cast)
//      font.draw(batch, state, this.getX, this.getY)
//    }
//    if (this.city != null) this.city.draw(batch)
//    if (this.highlight != null) batch.draw(highlight, this.getX, this.getY, this.getOriginX, this.getOriginY, this.getWidth, this.getHeight, this.getScaleX, this.getScaleY, 0)
//    super.draw(batch, parentAlpha)
//  }
//
//  def getSpeed: Float = speed
//
//  def setSpeed(speed: Float) = this.speed = speed
//
//  def dead: Boolean = this.combat != null && this.combat.getHp <= 0
//
//  def getWorld: WorldStage = {
//    val stage = this.getStage
//    val world = if (stage.isInstanceOf[WorldStage]) stage.asInstanceOf[WorldStage]
//    else null
//    world
//  }
//
//  def queueAction(action: Action) = {
//    var sequenceAction: SequenceAction = null
//    if (getActions().size == 0) {
//      sequenceAction = new SequenceAction()
//      getActions.add(sequenceAction)
//    }
//    sequenceAction.addAction(action)
//  }
//
//  def setOrder(o: Order) = {
//    this.order = o
//    this.order.start()
//  }
//
//  def handle(event: Event) = {
//
//  }
//
//  private def handleDevelop(event: Event): Boolean = {
//    if (!event.isInstanceOf[DevelopCityEvent]) return false
//    true
//  }
//
//  def getFaction: Faction = faction
//
//  def setFaction(faction: Faction) = this.faction = faction
//
//  def getAbilityComponent: AbilityComponent = abilityComponent
//
//  def setAbilityComponent(abilityComponent: AbilityComponent) = this.abilityComponent = abilityComponent
//}
